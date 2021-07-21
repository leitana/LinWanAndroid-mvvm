package com.lx.linwanandroid_mvvm.audioRecordService

/**
 * @title：AppRecorder
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/20
 */
interface AppRecorder {
    fun addRecordingCallback(recorderCallback: RecorderContract.RecorderCallback)
    fun removeRecordingCallback(recorderCallback: RecorderContract.RecorderCallback)
    fun setRecorder(recorder: RecorderContract.Recorder)
    fun startRecording(filePath: String, channelCount: Int = 1, sampleRate: Int = 44100, bitrate: Int = 96000)
    fun pauseRecording()
    fun resumeRecording()
    fun stopRecording()
    fun getRecordingDuration(): Long
    fun isRecording(): Boolean
    fun isPaused(): Boolean
    fun release()
}