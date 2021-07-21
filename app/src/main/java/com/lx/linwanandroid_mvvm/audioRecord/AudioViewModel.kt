package com.lx.linwanandroid_mvvm.audioRecord

import android.content.Intent
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import cn.rongcloud.rtc.core.ContextUtils.getApplicationContext
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ServiceUtils.startService
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.AudioRecorder
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.RecorderListener
import com.lx.linwanandroid_mvvm.audioRecordService.RecordingService
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.constant.Constant.ACTION_START_RECORDING_SERVICE
import com.lx.linwanandroid_mvvm.utils.context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


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

    val path: String = PathUtils.getRootPathExternalFirst() + File.separator +  "wanandroid/audio"

    var audioList: MutableLiveData<MutableList<AudioBean>> = MutableLiveData()

    var audioFileList: MutableList<File> = mutableListOf()
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
        AudioRecorder.setRecorderCallback(object : RecorderListener.RecorderCallback {
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

//    suspend fun getAudioList(path: String){
//        launchOnIO {
//            val audioBeanList = mutableListOf<AudioBean>()
//            audioFileList = FileUtils.listFilesInDirWithFilter(path) { path.endsWith(".mp3") }
//            val mediaPlayer = MediaPlayer()
//            var audioDuration: Int
//            for (audio in audioFileList) {
//                mediaPlayer.setDataSource(audio.path)
//                mediaPlayer.prepare();
//                audioDuration = mediaPlayer.duration
//                val audioBean = AudioBean(audio.name, audioDuration, audio.path, false, 0)
//                audioBeanList.add(audioBean)
//            }
//            audioList.value = audioBeanList
//            mediaPlayer.stop()
//            mediaPlayer.reset()
//            mediaPlayer.release()
//        }
//    }
    suspend fun getAudioList() {
        withContext(Dispatchers.IO){
            val audioBeanList = mutableListOf<AudioBean>()
            audioFileList = FileUtils.listFilesInDirWithFilter(path
            ) { pathname -> pathname!!.name.endsWith(".mp3") }
            val mediaPlayer = MediaPlayer()
            var audioDuration: Int
            for (audio in audioFileList) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(audio.path)
                mediaPlayer.prepare()
                audioDuration = mediaPlayer.duration
                val audioBean = AudioBean(audio.name, audioDuration, audio.path, false, 0)
                audioBeanList.add(audioBean)
            }
            audioList.postValue(audioBeanList)
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
    }
    
    fun startRecordService(){
        val intent: Intent = Intent(context(), RecordingService::class.java)
        intent.action = ACTION_START_RECORDING_SERVICE
        startService(intent)
    }

}