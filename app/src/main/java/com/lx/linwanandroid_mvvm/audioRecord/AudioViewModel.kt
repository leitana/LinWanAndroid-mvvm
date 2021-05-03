package com.lx.linwanandroid_mvvm.audioRecord

import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.AudioRecorder
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.RecorderListener
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import java.io.File

/**
 * @title：AudioViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/01
 */
class AudioViewModel: BaseViewModel() {
    var audioList: MutableLiveData<MutableList<AudioBean>> = MutableLiveData()
    //是否正在录音
    var isRecording: MutableLiveData<Boolean> = MutableLiveData(false)
    //录音时长
    var audioTime: MutableLiveData<Long> = MutableLiveData(0)
    //音波振幅
    var audioAmplitude: MutableLiveData<Int> = MutableLiveData(0)


    fun recordRecord(outFile: String) {
        AudioRecorder.setRecorderCallback(object: RecorderListener.RecorderCallback{
            override fun onStartRecord(output: File) {
                isRecording.value = AudioRecorder.isRecording()
            }

            override fun onPauseRecord() {
                isRecording.value = AudioRecorder.isRecording()
            }

            override fun onResumeRecord() {
                isRecording.value = AudioRecorder.isRecording()
            }

            override fun onRecordProgress(mills: Long, amp: Int) {
                isRecording.value = AudioRecorder.isRecording()
                audioTime.value = mills
                audioAmplitude.value = amp
            }

            override fun onStopRecord(output: File) {
                isRecording.value = AudioRecorder.isRecording()
            }

            override fun onError(throwable: Exception) {
                isRecording.value = AudioRecorder.isRecording()
            }

        })
        AudioRecorder.startRecording(outFile)
    }

    fun resumeAudio() {
        AudioRecorder.resumeRecording()
    }

    fun pauseAudio() {
        AudioRecorder.pauseRecording()
    }

    fun stopAudio() {
        AudioRecorder.stopRecording()
    }

}