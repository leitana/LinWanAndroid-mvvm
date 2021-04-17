package com.lx.linwanandroid_mvvm.utils

/**
 * @title：Unbox
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/15
 */
object Unbox {

    fun unBox(byte: Byte?): Byte {
        return byte ?: 0
    }

    fun unBox(short: Short?): Short {
        return short ?: 0
    }

    fun unBox(box: Int?): Int{
        return box ?: 0
    }

    fun unBox(box: Long?): Long {
        return box ?: 0
    }

    fun unBox(box: Float?): Float {
        return box ?: 0f
    }

    fun unBox(box: Double?): Double {
        return box ?: 0.0
    }

    fun unBox(box: Char?): Char {
        return box ?: ("" as Char)
    }

    fun unBox(box: Boolean?): Boolean {
        return box ?: false
    }

    fun unBox(box: String?): String {
        return box ?: ""
    }
}