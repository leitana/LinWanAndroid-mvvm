package com.lx.linwanandroid_mvvm.videoCall.navigation

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseActivity
import com.lx.linwanandroid_mvvm.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_trtc_navigation.*

/**
 * @title：NavigationActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/30
 */
class NavigationActivity: BaseActivity(){

    lateinit var list: MutableList<RTCItemEntity>
    private lateinit var mAdapter: NavigationAdapter

    override fun getLaytouResId(): Int = R.layout.activity_trtc_navigation

    override fun initView() {
        mAdapter = NavigationAdapter()
        recyclerView.run {
            adapter = mAdapter
        }
    }

    override fun initData() {
        list = mutableListOf()
        list.add(
            RTCItemEntity(
                getString(R.string.item_video_conferencing),
                "语音自动降噪、视频画质超高清，适用于在线会议、远程培训、小班课等场景。",
                R.drawable.multi_meeting,
                0,
                LoginActivity::class.java
            )
        )
        list.add(
            RTCItemEntity(
                getString(R.string.item_chat_room),
                "内含变声、音效、混响、背景音乐等声音玩法，适用于闲聊房、K歌房、开黑房等语聊场景。",
                R.drawable.voice_chatroom,
                0,
                LoginActivity::class.java
            )
        )
        list.add(
            RTCItemEntity(
                getString(R.string.item_video_interactive_live_streaming),
                "低延时、十万人高并发的大型互动直播解决方案，观众时延低至800ms，上下麦切换免等待。",
                R.drawable.live_stream,
                0,
                LoginActivity::class.java
            )
        )
        list.add(
            RTCItemEntity(
                getString(R.string.item_voice_call),
                "48kHz高音质，60%丢包可正常语音通话，领先行业的3A处理，杜绝回声和啸叫。",
                R.drawable.voice_call,
                1,
                LoginActivity::class.java
            )
        )
        list.add(
            RTCItemEntity(
                getString(R.string.item_video_call),
                "支持720P/1080P高清画质，50%丢包率可正常视频通话，自带美颜、挂件、抠图等AI特效。",
                R.drawable.video_call,
                2,
                LoginActivity::class.java
            )
        )
        list.add(
            RTCItemEntity(
                getString(R.string.item_chat_salon), "", R.drawable.chat_salon, 0,
                LoginActivity::class.java
            )
        )
        mAdapter.setList(list)
    }


}