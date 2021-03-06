package com.lx.linwanandroid_mvvm.audioRecordService

import android.media.MediaRecorder
import android.os.Build
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
    const val PLAYBACK_VISUALIZATION_INTERVAL: Long =
        ((2.1 * 13).toLong())//mills

    const val TAG: String = "AudioRecorder"

    private val _isRecording = AtomicBoolean(false)
    private val _isPaused = AtomicBoolean(false)
    private val handler = Handler()

    private var recorder: MediaRecorder? = null
    private var recordFile: File? = null
    private var updateTime: Long = 0
    private var durationMills: Long = 0

    private var recorderCallback: RecorderContract.RecorderCallback? = null

    override fun setRecorderCallback(callback: RecorderContract.RecorderCallback) {
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
                if (recorderCallback != null) {
                    recorderCallback!!.onStartRecord(recordFile!!)
                }
                _isPaused.set(false)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && _isPaused.get()) {
            try {
                recorder!!.resume()
                updateTime = System.currentTimeMillis()
                scheduleRecordingTimeUpdate()
                if (recorderCallback != null) {
                    recorderCallback!!.onResumeRecord()
                }
                _isPaused.set(false)
            } catch (e: IllegalStateException) {
                Log.e(TAG, e.message)
                if (recorderCallback != null) {
                    recorderCallback!!.onError(e)
                }
            }
        }
    }

    override fun pauseRecording() {
        if (_isRecording.get()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (!_isPaused.get()) {
                    try {
                        recorder!!.pause()
                        durationMills += System.currentTimeMillis() - updateTime
                        pauseRecordingTimer()
                        if (recorderCallback != null) {
                            recorderCallback!!.onPauseRecord()
                        }
                        _isPaused.set(true)
                    } catch (e: IllegalStateException){
                        Log.e(TAG, e.message)
                        if (recorderCallback != null) {
                            recorderCallback!!.onError(e)
                        }
                    }
                }
            } else {
                stopRecording()
            }
        }
    }

    override fun stopRecording() {
        if (_isRecording.get()) {
            stopRecordingTimer()
            try {
                recorder!!.stop()
            } catch (e: RuntimeException) {
                Log.e(TAG, "stopRecording() problems")
            }
            recorder!!.release()
            if (recorderCallback != null) {
                recordFile?.let { recorderCallback!!.onStopRecord(it) }
            }
            durationMills = 0
            recordFile = null
            _isRecording.set(false)
            _isPaused.set(false)
            recorder = null
        } else {
            Log.e(TAG, "Recording has already stopped or hasn't started")
        }
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
                    Log.e("AudioRecorder", e.message)
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

    override val isRecording: Boolean
        get() = _isRecording.get()
    override val isPaused: Boolean
        get() = _isPaused.get()
}