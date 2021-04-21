package com.lx.linwanandroid_mvvm.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lx.linwanandroid_mvvm.widgets.SpaceItemDecoration

/**
 * @title：BindingRecyclerView
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/20
 */

@BindingAdapter("adpater")
fun RecyclerView.adapter(adapter: RecyclerView.Adapter<*>){
    setAdapter(adapter)
}

@BindingAdapter("itemTopPadding", "itemLeftPadding", "itemBottomPadding", "itemRightPadding",requireAll = false)
fun RecyclerView.addItemPadding(top: Int = 0, left: Int = 0, bottom: Int = 0, right: Int = 0) {
    addItemDecoration(SpaceItemDecoration(top, left, bottom, right))
}