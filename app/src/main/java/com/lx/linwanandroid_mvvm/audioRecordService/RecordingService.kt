package com.lx.linwanandroid_mvvm.audioRecordService

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.audioRecordService.RecorderContract.RecorderCallback
import com.lx.linwanandroid_mvvm.constant.Constant.ACTION_START_RECORDING_SERVICE
import com.lx.linwanandroid_mvvm.constant.Constant.ACTION_STOP_RECORDING_SERVICE
import com.lx.linwanandroid_mvvm.ui.main.MainActivity
import java.io.File

/**
 * @title：RecordingService
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/16
 */
class RecordingService: Service() {
    private val CHANNEL_NAME = "Default"
    private val CHANNEL_ID = "com.lx.linwanandroid_mvvm.NotificationId"

    val ACTION_STOP_RECORDING = "ACTION_STOP_RECORDING"
    val ACTION_PAUSE_RECORDING = "ACTION_PAUSE_RECORDING"

    private val NOTIF_ID = 101

    private var appRecorder: AppRecorder? = null
    private val audioRecorder: RecorderContract.Recorder = AudioRecorder
    private lateinit var recorderCallback: RecorderCallback
    private lateinit var notificationManager: NotificationManager
    private lateinit var remoteViewsSmall: RemoteViews
    private var notification: Notification? = null

    private var started = false

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        appRecorder = AppRecorderImpl.getInstance(audioRecorder)
        recorderCallback = object: RecorderCallback{
            override fun onStartRecord(output: File) {
                updateNotificationResume()
            }

            override fun onPauseRecord() {
                updateNotificationPause()
            }

            override fun onResumeRecord() {
                updateNotificationResume()
            }

            override fun onRecordProgress(mills: Long, amp: Int) {
            }

            override fun onStopRecord(output: File) {
                stopForegroundService()
            }

            override fun onError(throwable: Exception?) {
            }
        }
        appRecorder!!.addRecordingCallback(recorderCallback)
//        audioRecorder.setRecorderCallback(recorderCallback)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            if (action != null && action.isNotEmpty()) {
                when (action) {
                    ACTION_START_RECORDING_SERVICE -> if (!started) {
                        startForegroundService()
                    }
                    ACTION_STOP_RECORDING_SERVICE -> stopForegroundService()
                    ACTION_STOP_RECORDING -> stopRecording()
                    ACTION_PAUSE_RECORDING -> if (appRecorder!!.isPaused()) {
                        appRecorder!!.resumeRecording()
                    } else {
                        appRecorder!!.pauseRecording()
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopRecording() {
        appRecorder!!.stopRecording()
        stopForegroundService()
    }

    fun startForegroundService() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
        }
        remoteViewsSmall = RemoteViews(packageName, R.layout.layout_record_notification_small)
        remoteViewsSmall.setOnClickPendingIntent(R.id.btn_recording_pause, getPendingSelfIntent(
            applicationContext, ACTION_PAUSE_RECORDING
        ))
        remoteViewsSmall.setOnClickPendingIntent(R.id.btn_recording_stop, getPendingSelfIntent(
            applicationContext, ACTION_STOP_RECORDING
        ))
        remoteViewsSmall.setTextViewText(R.id.txt_recording_progress, "正在录音...")

        // Create notification builder.
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setWhen(System.currentTimeMillis())
        builder.setSmallIcon(R.drawable.ic_record_rec)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_MAX
        } else {
            builder.priority = Notification.PRIORITY_MAX
        }

        builder.setContentIntent(createContentIntent())
        builder.setCustomContentView(remoteViewsSmall)
        //		builder.setCustomBigContentView(remoteViewsBig);
        builder.setOnlyAlertOnce(true)
        builder.setDefaults(0)
        builder.setSound(null)
        notification = builder.build()
        startForeground(NOTIF_ID, notification)
        started = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String) {
        val channel = notificationManager.getNotificationChannel(channelId)
        if (channel == null) {
            val chan =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            chan.setSound(null, null)
            chan.enableLights(false)
            chan.enableVibration(false)
            notificationManager.createNotificationChannel(chan)
        }
    }

    private fun updateNotificationResume() {
        if (started) {
            remoteViewsSmall.setTextViewText(R.id.txt_recording_progress, "正在录音...")
            remoteViewsSmall.setImageViewResource(R.id.btn_recording_pause, R.drawable.ic_pause_new)
            notificationManager.notify(NOTIF_ID, notification)
        }
    }

    private fun updateNotificationPause() {
        if (started) {
            remoteViewsSmall.setTextViewText(R.id.txt_recording_progress, "已暂停")
            remoteViewsSmall.setImageViewResource(R.id.btn_recording_pause, R.drawable.ic_recording_yellow)
            notificationManager.notify(NOTIF_ID, notification)
        }
    }

    private fun createContentIntent(): PendingIntent? {
        // Create notification default intent.
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
        return PendingIntent.getActivity(applicationContext, 0, intent, 0)
    }

    private fun stopForegroundService() {
        appRecorder!!.removeRecordingCallback(recorderCallback)
        stopForeground(true)
        stopSelf()
        started = false
    }

    fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, StopRecordingReceiver::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 10, intent, 0)
    }

    class StopRecordingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val stopIntent = Intent(context, RecordingService::class.java)
            stopIntent.action = intent.action
            context.startService(stopIntent)
        }
    }
}