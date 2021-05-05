package com.lx.linwanandroid_mvvm.audioRecord

import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.AudioRecorder
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.RecorderListener
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import java.io.File
import java.lang.StringBuilder

/**
 * @title：AudioViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/01
 */
class AudioViewModel: BaseViewModel() {

    enum class RecordStates {
        Start, Pause, Resume, Stop, Error
    }

    var audioList: MutableLiveData<MutableList<AudioBean>> = MutableLiveData()
    //是否正在录音
    var isRecording: MutableLiveData<Boolean> = MutableLiveData(false)

    var recordState: MutableLiveData<RecordStates> = MutableLiveData(RecordStates.Stop)
    //录音时长
    var audioTime: MutableLiveData<Long> = MutableLiveData(0)
    //音波振幅
    var audioAmplitude: MutableLiveData<Int> = MutableLiveData(0)

    val ayduiTimeStr: MutableLiveData<StringBuilder> = MutableLiveData()

    private val audioTimeString: StringBuilder = StringBuilder("0:0")

    fun recordRecord(outFile: String) {
        AudioRecorder.setRecorderCallback(object: RecorderListener.RecorderCallback{
            override fun onStartRecord(output: File) {
                recordState.value = RecordStates.Start
                isRecording.value = true
            }

            override fun onPauseRecord() {
                recordState.value = RecordStates.Pause
//                isRecording.value = true
            }

            override fun onResumeRecord() {
                recordState.value = RecordStates.Resume
                isRecording.value = true
            }

            override fun onRecordProgress(mills: Long, amp: Int) {
//                isRecording.value = AudioRecorder.isRecording()
                audioTime.value = mills
                audioAmplitude.value = amp

                audioTimeString.run {
                    append((audioTime.value!! / (60 * 1000)))
                    append(":")
                    append((audioTime.value!! % (60 * 1000)))
                }

            }

            override fun onStopRecord(output: File) {
                recordState.value = RecordStates.Stop
                isRecording.value = false
            }

            override fun onError(throwable: Exception) {
                recordState.value = RecordStates.Error
                isRecording.value = false
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