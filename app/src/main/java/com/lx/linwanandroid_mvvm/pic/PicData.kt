package com.lx.linwanandroid_mvvm.pic

import com.github.iielse.imageviewer.adapter.ItemType
import com.github.iielse.imageviewer.core.Photo

/**
 * @title：PicData
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/10/23
 */
class PicData(
    val id: Long,
    val url: String): Photo {
    override fun id(): Long = id

    override fun itemType(): Int = ItemType.PHOTO
}