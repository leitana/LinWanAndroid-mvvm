package com.lx.linwanandroid_mvvm.audioRecordService

import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @title：AudioRecorder
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/15
 */
object AudioRecorder: RecorderContract.Recorder {
    /** Time interval for Recording progress visualisation.  */
    const val RECORDING_VISUALIZATION_INTERVAL: Long = 13 //mills
    const val TAG: String = "AudioRecorder"

    private val _isRecording = AtomicBoolean(false)
    private val _isPaused = AtomicBoolean(false)
    private val handler = Handler()

    private var recorder: MediaRecorder? = null
    private var recordFile: File? = null
    private var updateTime: Long = 0
    private var durationMills: Long = 0

    private var recorderCallback: RecorderContract.RecorderCallback? = null

    override fun setRecorderCallback(callback: RecorderContract.RecorderCallback?) {
        this.recorderCallback = callback
    }

    override fun startRecording(outputFile: String, channelCount: Int, sampleRate: Int, bitrate: Int) {
        recordFile = File(outputFile)
        if (recordFile!!.exists() && recordFile!!.isFile) {
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC) // 设置音频的来源
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 设置音频的输出格式
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // 设置音频的编码方式
            recorder!!.setAudioChannels(channelCount) // 设置录制的音频通道数
            recorder!!.setAudioSamplingRate(sampleRate) // 采样频率
            recorder!!.setAudioEncodingBitRate(bitrate) // 设置录制的音频编码比特率
            //设置记录会话的最大持续时间（毫秒）
            recorder!!.setMaxDuration(-1) //Duration unlimited or use RECORD_MAX_DURATION
            recorder!!.setOutputFile(recordFile!!.absolutePath)
            try {
                recorder!!.prepare()
                recorder!!.start()
                updateTime = System.currentTimeMillis()
                _isRecording.set(true)
                scheduleRecordingTimeUpdate()
            } catch (e: IOException) {
                Log.e("TAG", "prepare() failed")
                if (recorderCallback != null) {
                    recorderCallback!!.onError(e)
                }
            } catch (e: IllegalStateException) {
                Log.e("TAG", "prepare() failed")
                if (recorderCallback != null) {
                    recorderCallback!!.onError(e)
                }
            }
        } else{
            if (recorderCallback != null) {
                recorderCallback!!.onError(IllegalStateException())
            }
        }
    }

    override fun resumeRecording() {
        TODO("Not yet implemented")
    }

    override fun pauseRecording() {
        TODO("Not yet implemented")
    }

    override fun stopRecording() {
        TODO("Not yet implemented")
    }

    private fun scheduleRecordingTimeUpdate(){
        handler.postDelayed({
            if (recorderCallback != null && recorder != null) {
                try {
                    val curTime = System.currentTimeMillis()
                    durationMills += curTime - updateTime
                    updateTime = curTime
                    recorderCallback!!.onRecordProgress(durationMills, recorder!!.maxAmplitude)
                } catch (e: IllegalStateException) {
                    Log.d("AudioRecorder", e.message)
                }
                scheduleRecordingTimeUpdate()
            }
        }, RECORDING_VISUALIZATION_INTERVAL)
    }

    override val isRecording: Boolean
        get() = _isRecording.get()
    override val isPaused: Boolean
        get() = _isPaused.get()
}