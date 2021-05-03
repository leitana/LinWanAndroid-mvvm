package com.lx.linwanandroid_mvvm.audioRecord.audioRecord

import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @title：AudioRecorder
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/02
 */
object AudioRecorder: RecorderListener.Recorder {
    const val RECORDING_VISUALIZATION_INTERVAL: Long = 13
    const val TAG = "AUDIORECORDER"

    private var recorder: MediaRecorder? = null
    private var recordFile: File? = null
    private var updateTime: Long = 0
    private var durationMills: Long = 0

    private val isRecording = AtomicBoolean(false)
    private val isPaused = AtomicBoolean(false)
    private val handler = Handler()

    private lateinit var recorderCallback: RecorderListener.RecorderCallback

    override fun setRecorderCallback(callback: RecorderListener.RecorderCallback) {
        this.recorderCallback = callback
    }

    override fun startRecording(
        outputFile: String,
        channelCount: Int,
        sampleRate: Int,
        bitrate: Int
    ) {
        recordFile = File(outputFile)
        if (recordFile!!.exists() && recordFile!!.isFile) {
            recorder = MediaRecorder()
            //设置音频的来源
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            //设置音频的输出格式
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            //设置音频的编码方式
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            //设置录制的音频通道数
            recorder!!.setAudioChannels(channelCount)
            //采样频率
            recorder!!.setAudioSamplingRate(sampleRate)
            //设置录制的音频编码比特率
            recorder!!.setAudioEncodingBitRate(bitrate)
            //设置记录会话的最大持续时间（毫秒）
            recorder!!.setMaxDuration(-1) //Duration unlimited or use RECORD_MAX_DURATION
            //设置录音文件位置
            recorder!!.setOutputFile(recordFile!!.absolutePath)

            try {
                recorder!!.prepare()
                recorder!!.start()
                updateTime = System.currentTimeMillis()
                isRecording.set(true)
                scheduleRecordingTimeUpdate()
                recorderCallback.onStartRecord(recordFile!!)
                isPaused.set(false)
            } catch (e: Exception) {
                Log.e(TAG, "prepare() failed")
                recorderCallback.onError(e)
            }

        } else {
            Log.e(TAG, "INVALID_OUTPUT_FILE")
//            recorderCallback.onError(InvalidOutputFile())
        }
    }

    /**
     * 恢复录音
     */
    override fun resumeRecording() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N && isPaused.get()) {
            try {
                recorder!!.resume()
                updateTime = System.currentTimeMillis()
                scheduleRecordingTimeUpdate()
                recorderCallback.onResumeRecord()
                isPaused.set(false)
            } catch (e: Exception) {
                Log.e(TAG, "unpauseRecording() failed")
                recorderCallback.onError(e)
            }
        }
    }

    /**
     * 暂停录音
     */
    override fun pauseRecording() {
        if (isRecording.get()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    if (!isPaused.get()) {
                        try {
                            recorder!!.pause()
                            durationMills = (durationMills + System.currentTimeMillis() - updateTime)
                            pauseRecordingTimer()
                            recorderCallback.onPauseRecord()
                            isPaused.set(true)
                        } catch (e: Exception) {
                            Log.e(TAG, "pauseRecording() failed")
                            recorderCallback.onError(e)
                        }
                    }
                } else {
                    stopRecording()
                }
        }
    }

    /**
     * 结束录音
     */
    override fun stopRecording() {
        if (isRecording.get()) {
            stopRecordingTimer()
            try {
                recorder!!.stop()
            } catch (e: Exception) {
                Log.e(TAG, "stopRecording() problems")
            }
            recorder!!.release()
            recorderCallback.onStopRecord(recordFile!!)
            durationMills = 0
            recordFile = null
            isRecording.set(false)
            isPaused.set(false)
            recorder = null
        } else {
            Log.d(TAG, "Recording has already stopped or hasn't started")
        }
    }

    /**
     * 是否正在录音
     */
    override fun isRecording(): Boolean = isRecording.get()

    /**
     * 录音是否已暂停
     */
    override fun isPaused(): Boolean = isPaused.get()

    private fun scheduleRecordingTimeUpdate() {
        handler.postDelayed({
            if (recorder != null) {
                try {
                    val curTime = System.currentTimeMillis()
                    durationMills = (durationMills + curTime - updateTime)
                    updateTime = curTime
                    recorderCallback.onRecordProgress(
                        durationMills.toLong(),
                        recorder!!.maxAmplitude
                    )
                } catch (e: IllegalStateException) {
                    Log.e(TAG, e.message)
                }
                scheduleRecordingTimeUpdate()
            }
        }, RECORDING_VISUALIZATION_INTERVAL)
    }

    private fun stopRecordingTimer() {
        handler.removeCallbacksAndMessages(null)
        updateTime = 0
    }

    private fun pauseRecordingTimer() {
        handler.removeCallbacksAndMessages(null)
        updateTime = 0
    }

}