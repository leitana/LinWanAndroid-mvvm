package com.lx.linwanandroid_mvvm.audioRecord

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityAudiorecordBinding
import com.lx.linwanandroid_mvvm.model.bean.Title
import com.lx.linwanandroid_mvvm.utils.context
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：AudioActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/30
 */
class AudioActivity: BaseVMActivity() {

    private val audioViewModel by viewModel<AudioViewModel>()
    private val binding by binding<ActivityAudiorecordBinding>(R.layout.activity_audiorecord)

    override fun initView() {
        binding.run {
            viewModel = audioViewModel
            title = Title("登录", mThemeColor)
        }
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }


}