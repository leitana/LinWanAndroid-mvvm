package com.lx.linwanandroid_mvvm.audioRecord

import android.Manifest
import android.content.Context
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityAudiorecordBinding
import com.lx.linwanandroid_mvvm.model.bean.Title
import kotlinx.android.synthetic.main.activity_audiorecord.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.util.*

/**
 * @title：AudioActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/30
 */
@RuntimePermissions
class AudioActivity: BaseVMActivity() {

    private val audioViewModel by viewModel<AudioViewModel>()
    private val binding by binding<ActivityAudiorecordBinding>(R.layout.activity_audiorecord)
    private val audioAdapter by lazy {
        AudioAdapter(mutableListOf())
    }


    override fun initView() {
        binding.run {
            viewModel = audioViewModel
            adapter = audioAdapter
            handler = this@AudioActivity
            title = Title("登录", mThemeColor)
            binding.root.ivStart.setOnClickListener {
                startRecordWithPermissionCheck(root.rootView.ivStart)
            }
            binding.root.ivPause.run {
                setOnClickListener {
                    when(audioViewModel.recordState.value) {
                        AudioViewModel.RecordStates.Start -> {
                            audioViewModel.pauseAudio()
                        }
                        AudioViewModel.RecordStates.Resume -> {
                            audioViewModel.pauseAudio()
                        }
                        AudioViewModel.RecordStates.Pause -> {
                            audioViewModel.resumeAudio()
                        }
                        AudioViewModel.RecordStates.Stop -> {

                        }
                        AudioViewModel.RecordStates.Error -> {

                        }
                    }
//                    if (audioViewModel.isRecording.value == true) {
//                        audioViewModel.pauseAudio()
//                    } else {
//                        audioViewModel.resumeAudio()
//                    }
                }
            }
        }
    }


    override fun initData() {
    }

    override fun startObserve() {
        audioViewModel.run {
            audioList.observe(this@AudioActivity, {
                audioAdapter.setData(it)
            })

            recordState.observe(this@AudioActivity, {
                when (it) {
                    AudioViewModel.RecordStates.Start -> {
                        binding.root.ivPause.setImageResource(R.drawable.ic_pause)
                    }
                    AudioViewModel.RecordStates.Resume -> {
                        binding.root.ivPause.setImageResource(R.drawable.ic_pause)
                    }
                    AudioViewModel.RecordStates.Pause -> {
                        binding.root.ivPause.setImageResource(R.drawable.ic_resume)
                    }
                    AudioViewModel.RecordStates.Stop -> {

                    }
                    AudioViewModel.RecordStates.Error -> {

                    }
                }
            })

            audioTime.observe(this@AudioActivity, {
            })

            audioAmplitude.observe(this@AudioActivity, {
//                Log.d("1111111", it.toString())
                binding.root.waveView.setPerHeight(it.toDouble() / 8000)
//                binding.root.waveView.setPerHeight(it.toDouble() / 5000)
            })
        }
    }
    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    fun startRecord(view: View) {
        val path = PathUtils.getRootPathExternalFirst() + File.separator +  "wanandroid/audio" + System.currentTimeMillis() + ".mp3"

        FileUtils.createOrExistsFile(path)
        audioViewModel.recordRecord(path)
    }

    fun stopRecord(view: View) {
        audioViewModel.stopAudio()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun getRootDir(context: Context): String? {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 优先获取SD卡根目录[/storage/sdcard0]
            getExternalStorageDirectory().absolutePath
        } else {
            // 应用缓存目录[/data/data/应用包名/cache]
            context.cacheDir.absolutePath
        }
    }
}