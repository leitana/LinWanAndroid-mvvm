package com.lx.linwanandroid_mvvm.widgets.splash_dot

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.lx.linwanandroid_mvvm.R
import kotlinx.android.synthetic.main.splash_dot.view.*

/**
 * @author linxiao
 * @title：SplashDot
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @data Created in 2021/07/11
</Description> */
@SuppressLint("UseCompatLoadingForDrawables")
class SplashDot @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val params = LayoutParams(20, 20)
    private val myContext = context

    var totalIndex: Int = 0
        set(value) {
            field = value
        }
    var curIndex: Int = 0
        set(value) {
            if (value == curIndex) {
                return
            }
            field = value
            removeAllViews()
            for (index in 1..totalIndex) {
                if (index == curIndex) {
                    val light = View(myContext)
                    light.run {
                        background = myContext?.getDrawable(R.drawable.shape_light_dot)
                        setPadding(10, 10, 10, 10)
                        layoutParams = params
                    }
                    addView(light)
                } else {
                    val gray = View(myContext)
                    gray.run {
                        background = myContext?.getDrawable(R.drawable.shape_gray_dot)
                        setPadding(10, 10, 10, 10)
                        layoutParams = params
                    }
                    addView(gray)
                }
            }
        }

    init {
        orientation = HORIZONTAL
        background = context?.getDrawable(R.drawable.bg_transparent)
        params.marginEnd = 8
        params.marginStart = 8
    }
}