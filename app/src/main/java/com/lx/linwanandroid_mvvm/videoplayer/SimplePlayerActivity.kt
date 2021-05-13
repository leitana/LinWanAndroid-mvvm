package com.lx.linwanandroid_mvvm.videoplayer

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lx.linwanandroid_mvvm.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_simpleplayer.*

/**
 * @title：SimplePlayerActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/08
 */
class SimplePlayerActivity: AppCompatActivity() {

    var orientationUtils: OrientationUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simpleplayer)

        val source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        videoPlayer.setUp(source1, true, "测试视频")

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.drawable.ic_video)
        videoPlayer.thumbImageView = imageView
        //增加title
        videoPlayer.titleTextView.visibility = View.VISIBLE
        //增加返回键
        videoPlayer.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, videoPlayer)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.fullscreenButton.setOnClickListener { orientationUtils!!.resolveByClick() }
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true)
        //设置返回按键功能
        videoPlayer.backButton.setOnClickListener { onBackPressed() }
//        videoPlayer.startPlayLogic()

    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) orientationUtils!!.releaseListener()
    }

    override fun onBackPressed() {

        //先返回正常状态
        if (orientationUtils!!.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.fullscreenButton.performClick()
            return
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }
}