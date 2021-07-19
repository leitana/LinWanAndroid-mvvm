package com.lx.linwanandroid_mvvm.audioRecordService

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.audioRecordService.RecorderContract.RecorderCallback
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


    private val audioRecorder: RecorderContract.Recorder = AudioRecorder
    private lateinit var recorderCallback: RecorderCallback
    private lateinit var notificationManager: NotificationManager
    private lateinit var remoteViewsSmall: RemoteViews

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        recorderCallback = object: RecorderCallback{
            override fun onStartRecord(output: File?) {

            }

            override fun onPauseRecord() {
            }

            override fun onResumeRecord() {
            }

            override fun onRecordProgress(mills: Long, amp: Int) {
            }

            override fun onStopRecord(output: File?) {
            }

            override fun onError(throwable: Exception?) {
            }
        }
        audioRecorder.setRecorderCallback(recorderCallback)
    }

    fun startForegroundService() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
        }
        remoteViewsSmall = RemoteViews(packageName, R.layout.layout_record_notification_small)
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

    class StopRecordingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val stopIntent = Intent(context, RecordingService::class.java)
            stopIntent.action = intent.action
            context.startService(stopIntent)
        }
    }
}