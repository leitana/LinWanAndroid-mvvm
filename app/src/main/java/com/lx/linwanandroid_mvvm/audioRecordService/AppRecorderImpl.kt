package com.lx.linwanandroid_mvvm.audioRecordService

import com.lx.linwanandroid_mvvm.audioRecordService.AudioRecorder.PLAYBACK_VISUALIZATION_INTERVAL
import java.io.File
import java.util.*

/**
 * @title：AppRecorderImpl
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/20
 */
class AppRecorderImpl private constructor(recorder: RecorderContract.Recorder) : AppRecorder {
    private var audioRecorder: RecorderContract.Recorder = recorder
    private var recorderCallback: RecorderContract.RecorderCallback
    private var appCallbacks: MutableList<RecorderContract.RecorderCallback> = mutableListOf()

    private val recordingData: MutableList<Int> = mutableListOf()
    private var apmpPool: MutableList<Int> = mutableListOf()
    private var durationMills: Long = 0
    private var updateTime: Long = 0
    private var timerProgress: Timer? = null

    companion object{
        @Volatile
        private var instance: AppRecorderImpl? = null
        @JvmStatic
        fun getInstance(recorder: RecorderContract.Recorder): AppRecorderImpl?{
            if (instance == null) {
                synchronized(AppRecorderImpl::class.java) {
                    if (instance == null) {
                        instance = AppRecorderImpl(recorder)
                    }
                }
            }
            return instance
        }
    }

    init {
        recorderCallback = object: RecorderContract.RecorderCallback{
            override fun onStartRecord(output: File) {
                durationMills = 0
                scheduleRecordingTimeUpdate()
                onRecordingStarted(output)
            }

            override fun onPauseRecord() {
                onRecordingPaused()
                pauseRecordingTimer()
            }

            override fun onResumeRecord() {
                scheduleRecordingTimeUpdate()
                onRecordingResumed()
            }

            override fun onRecordProgress(mills: Long, amp: Int) {
                apmpPool.add(amp)
            }

            override fun onStopRecord(output: File) {
                stopRecordingTimer()
                onRecordingStopped(output)
            }

            override fun onError(throwable: Exception?) {
                onRecordingError(throwable!!)
            }
        }
    }

    override fun addRecordingCallback(recorderCallback: RecorderContract.RecorderCallback) {
        appCallbacks.add(recorderCallback)
    }

    override fun removeRecordingCallback(recorderCallback: RecorderContract.RecorderCallback) {
        appCallbacks.remove(recorderCallback)
    }

    override fun setRecorder(recorder: RecorderContract.Recorder) {
        audioRecorder = recorder
        audioRecorder.setRecorderCallback(recorderCallback)
    }

    override fun startRecording(filePath: String, channelCount: Int, sampleRate: Int, bitrate: Int) {
        if (!audioRecorder.isRecording) {
            audioRecorder.startRecording(filePath, channelCount, sampleRate, bitrate)
        }
    }

    override fun pauseRecording() {
        if (audioRecorder.isRecording) {
            audioRecorder.pauseRecording()
        }
    }

    override fun resumeRecording() {
        if (audioRecorder.isPaused) {
            audioRecorder.resumeRecording()
        }
    }

    override fun stopRecording() {
        if (audioRecorder.isRecording) {
            audioRecorder.stopRecording()
        }
    }

    override fun getRecordingDuration(): Long {
        return durationMills
    }

    override fun isRecording(): Boolean {
        return audioRecorder.isRecording
    }

    override fun isPaused(): Boolean {
        return audioRecorder.isPaused
    }

    override fun release() {
        stopRecordingTimer()
        recordingData.clear()
        apmpPool.clear()
        audioRecorder.stopRecording()
        appCallbacks.clear()
    }

    private fun onRecordingStarted(output: File){
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onStartRecord(output)
            }
        }
    }

    private fun onRecordingPaused(){
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onPauseRecord()
            }
        }
    }

    private fun onRecordingResumed() {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onResumeRecord()
            }
        }
    }

    private fun onRecordingStopped(file: File) {
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onStopRecord(file)
            }
        }
    }

    private fun onRecordingProgress(mills: Long, amp: Int){
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onRecordProgress(mills, amp)
            }
        }
    }

    private fun onRecordingError(e: Exception){
        if (appCallbacks.isNotEmpty()) {
            for (i in appCallbacks.indices) {
                appCallbacks[i].onError(e)
            }
        }
    }

    private fun scheduleRecordingTimeUpdate(){
        updateTime = System.currentTimeMillis()
        timerProgress = Timer()
        timerProgress!!.schedule(object : TimerTask(){
            override fun run() {
                readProgress()
                val curTime = System.currentTimeMillis()
                durationMills += curTime - updateTime
                updateTime = curTime
            }

        },0, PLAYBACK_VISUALIZATION_INTERVAL)
    }

    private fun readProgress() {
        if (apmpPool.size > 0) {
            val amp = apmpPool[apmpPool.size - 1]
            apmpPool.clear()
            apmpPool.add(amp)
            recordingData.add(amp)
            onRecordingProgress(durationMills, amp)
        }
    }

    private fun pauseRecordingTimer(){
        timerProgress?.run {
            cancel()
            purge()
        }
        updateTime = 0
    }

    private fun stopRecordingTimer() {
        readProgress()
        timerProgress?.run {
            cancel()
            purge()
        }
        updateTime = 0
    }
}