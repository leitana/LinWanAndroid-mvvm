package com.lx.linwanandroid_mvvm.videoCall.navigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lx.linwanandroid_mvvm.R
import io.rong.callkit.RongCallKit
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import kotlinx.android.synthetic.main.activity_call_single.*

/**
 * @title：RongCallActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/20
 */
class RongCallActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_single)
        //userId = 000001
        //TXiVm9Mt1A0eRAyyzey6Lhwo9Rf+3zrQ7n20kVVesAg=@a2e0.cn.rongnav.com;a2e0.cn.rongcfg.com
        //userId = 000002
        //TXiVm9Mt1A0YSZ8wzy0chBwo9Rf+3zrQtSGjoOpwbkc=@a2e0.cn.rongnav.com;a2e0.cn.rongcfg.com
        RongIM.connect("TXiVm9Mt1A0YSZ8wzy0chBwo9Rf+3zrQtSGjoOpwbkc=@a2e0.cn.rongnav.com;a2e0.cn.rongcfg.com",
            object : RongIMClient.ConnectCallback() {
                override fun onSuccess(p0: String?) {
                    //连接成功
                    Log.e("2222222", "onSuccess$p0")
//                    RongCallKit.onViewCreated()
                }

                override fun onError(errorCode: RongIMClient.ConnectionErrorCode?) {
                    if(errorCode!! == RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT) {
                        //从 APP 服务获取新 token，并重连
                    } else {
                        //无法连接 IM 服务器，请根据相应的错误码作出对应处理
                    }
                }

                override fun onDatabaseOpened(p0: RongIMClient.DatabaseOpenStatus?) {
                    //消息数据库打开，可以进入到主页面
                    Log.e("2222222", p0.toString())
                }
            })

        button1.setOnClickListener {
            RongCallKit.startSingleCall(this, "000001", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO)
        }


    }
}