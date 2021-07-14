package com.lx.linwanandroid_mvvm.audioRecord

import android.Manifest
import android.content.Context
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityAudiorecordBinding
import com.lx.linwanandroid_mvvm.model.bean.Title
import kotlinx.android.synthetic.main.activity_audiorecord.view.*
import kotlinx.coroutines.launch
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
        AudioAdapter(mutableListOf(), this@AudioActivity)
    }


    override fun initView() {
        binding.run {
            audioAdapter.setHasStableIds(true)
            viewModel = audioViewModel
            adapter = audioAdapter
            handler = this@AudioActivity
            title = Title("录音", mThemeColor)
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
        lifecycleScope.launch {
            audioViewModel.getAudioList()
        }
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
                        initData()
                    }
                    AudioViewModel.RecordStates.Error -> {

                    }
                }
            })

            audioTime.observe(this@AudioActivity, {
            })

            audioAmplitude.observe(this@AudioActivity, {
//                Log.d("1111111", it.toString())
                binding.root.waveView.update(it)
//                binding.root.waveView.setPerHeight(it.toDouble() / 5000)
            })

            isRecording.observe(this@AudioActivity, {
                if (it){
                    binding.llWave.visibility = View.VISIBLE
                    val mShowAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                    mShowAction.duration = 500
                    binding.llWave.startAnimation(mShowAction)
                } else {
                    val mCloseAction  = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f)
                    mCloseAction.duration = 500
                    mCloseAction.setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                        }
                        override fun onAnimationEnd(animation: Animation?) {
                            binding.llWave.visibility = View.GONE
                        }
                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                    })
                    binding.llWave.startAnimation(mCloseAction)
                }
            })
        }
    }
    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    fun startRecord(view: View) {
        val path = audioViewModel.path + File.separator + System.currentTimeMillis() + ".mp3"

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
        onRequestPermissionsResult(requestCode, grantResults)
    }

}