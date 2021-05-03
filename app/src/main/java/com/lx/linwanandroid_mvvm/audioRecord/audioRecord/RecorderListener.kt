/*
 * Copyright 2018 Dmytro Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lx.linwanandroid_mvvm.audioRecord.audioRecord

import java.io.File

interface RecorderListener {
    interface RecorderCallback {
        fun onStartRecord(output: File)
        fun onPauseRecord()
        fun onResumeRecord()
        fun onRecordProgress(mills: Long, amp: Int)
        fun onStopRecord(output: File)
        fun onError(throwable: Exception)
    }

    interface Recorder {
        fun setRecorderCallback(callback: RecorderCallback)
        fun startRecording(outputFile: String, channelCount: Int = 1, sampleRate: Int = 44100, bitrate: Int = 96000)
        fun resumeRecording()
        fun pauseRecording()
        fun stopRecording()
        fun isRecording(): Boolean
        fun isPaused(): Boolean
    }
}