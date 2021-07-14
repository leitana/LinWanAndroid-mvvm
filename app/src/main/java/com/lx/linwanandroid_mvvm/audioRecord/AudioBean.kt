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
                var progress: Int = 0) {

    override fun equals(o: Any?): Boolean {
//        return super.equals(other)
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val task: AudioBean = o as AudioBean

        return filePath == task.filePath
    }

    override fun hashCode(): Int {
        return filePath.hashCode()
    }
}