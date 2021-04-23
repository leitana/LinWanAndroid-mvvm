package com.lx.linwanandroid_mvvm.ext

import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.module.BaseLoadMoreModule

/**
 * @title：LoadMoreExt
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/23
 */
fun BaseLoadMoreModule.setLoadMoreStatus(loadMoreStatus: LoadMoreStatus){
    when (loadMoreStatus) {
        LoadMoreStatus.Complete -> loadMoreComplete()
        LoadMoreStatus.Fail -> loadMoreFail()
        LoadMoreStatus.End -> loadMoreEnd()
        else -> return
    }
}