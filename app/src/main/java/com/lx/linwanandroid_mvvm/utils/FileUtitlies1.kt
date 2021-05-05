package com.lx.linwanandroid_mvvm.utils

import android.content.Context
import android.os.Environment
import java.io.File

object FileUtits1 {

    fun getAudioDir(context: Context): String{
        val dir = getRootDir(context) + File.separator + "wanandroid/audio" + File.separator
        return checkDir(dir)
    }


    private fun checkDir(dir: String): String {
        val directory = File(dir)
        if (!directory.exists() || !directory.isDirectory) {
            directory.mkdirs()
        }
        return dir
    }

    fun getDownloadPath(context: Context, name: String): String {
        return getRootDir(context) + File.separator + name
    }

    fun deleteFile(path: String?) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    fun getRootDir(context: Context): String? {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 优先获取SD卡根目录[/storage/sdcard0]
            Environment.getExternalStorageDirectory().absolutePath
        } else {
            // 应用缓存目录[/data/data/应用包名/cache]
            context.cacheDir.absolutePath
        }
    }
}