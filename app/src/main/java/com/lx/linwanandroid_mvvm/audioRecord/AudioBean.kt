package com.lx.linwanandroid_mvvm.audioRecord

/**
 * @title：AudioBean
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/01
 */
class AudioBean(var fileName: String,
                var audioDuration: Int,
                var filePath: String,
                var isSelect: Boolean = false,
                var progress: Int = 0)