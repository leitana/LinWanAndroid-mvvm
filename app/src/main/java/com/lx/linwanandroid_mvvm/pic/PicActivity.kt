package com.lx.linwanandroid_mvvm.pic

import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import com.github.iielse.imageviewer.ImageViewerBuilder
import com.github.iielse.imageviewer.core.DataProvider
import com.hitomi.tilibrary.style.index.NumberIndexIndicator
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityPicBinding
import com.lx.linwanandroid_mvvm.pic.TransitionViewsRef.KEY_MAIN
import com.vansz.glideimageloader.GlideImageLoader

/**
 * @title：PicActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/10/23
 */
class PicActivity: BaseVMActivity() {
    private val binding by binding<ActivityPicBinding>(R.layout.activity_pic)

    private val picAdapter: PicAdapter by lazy { PicAdapter() }

    private val list: MutableList<PicData> = mutableListOf()

    private val urlList: MutableList<String> = mutableListOf()

    var transferee: Transferee? = null

    var loadAllAtOnce = false

    override fun initView() {
        binding.run {
            titleBar.tvTitle.text = "图片预览"
            binding.picRecyclerView.layoutManager = GridLayoutManager(this@PicActivity, 3)
            binding.picRecyclerView.adapter = picAdapter
        }
        picAdapter.setList(urlList)

        urlList.addAll(image.toList())
        picAdapter.setList(urlList)
        picAdapter.setOnItemClickListener { adapter, view, position ->
//            val builder = ImageViewerBuilder(
//                context = this,
//                initKey = list[position].id, // 用于定位被点击缩略图变化大图后初始化所在位置.以此来执行过渡动画.
//                dataProvider = provideViewerDataProvider { list }, // 浏览数据源的提供者.支持一次性给全数据或分页加载.
//                imageLoader = MyImageLoader(), // 实现对数据源的加载.支持自定义加载数据类型，加载方案
//                transformer = MyTransformer(KEY_MAIN) // 以photoId为标示，设置过渡动画的'配对'.
//            )
//            builder.show()

            val transferee = Transferee.getDefault(this)
            val config = TransferConfig.build()
//                .setSourceImageList(sourceUrlList) // 资源 url 集合, String 格式
                .setSourceUrlList(urlList) // 资源 uri 集合， Uri 格式
                .setMissPlaceHolder(R.drawable.ic_empty_photo) // 资源加载前的占位图
                .setErrorPlaceHolder(R.drawable.ic_empty_photo) // 资源加载错误后的占位图
                .setProgressIndicator(ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setIndexIndicator(NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                .setImageLoader(GlideImageLoader.with(getApplicationContext())) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
//                .setCustomView(customView) // 自定义视图，将放在 transferee 的面板上
                .setNowThumbnailIndex(position) // 缩略图在图组中的索引
                .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
//                .setOnLongClickListener(new Transferee.OnTransfereeLongClickListener() { // 长按当前页面监听器
//                    @Override
//                    public void onLongClick(ImageView imageView, String imageUri, int pos) {
//                        saveImageFile(imageUri); // 使用 transferee.getFile(imageUri) 获取缓存文件保存，视频不支持
//                    }
//                })
//                .bindImageView(imageView, source) // 绑定一个 ImageView, 所有绑定方法只能调用一个
//                .bindListView(listView, R.id.iv_thumb) // 绑定一个 ListView， 所有绑定方法只能调用一个
                .bindRecyclerView(binding.picRecyclerView, R.id.imageView)  // 绑定一个 RecyclerView， 所有绑定方法只能调用一个
            transferee.apply(config).show()
        }
    }

    override fun initData() {
//        var id = 0L
//        list.addAll(image.map { PicData(id = id++, url = it) })
//        picAdapter.setList(list)
    }

    override fun startObserve() {
    }

    override fun onDestroy() {
        super.onDestroy()
//        TransitionViewsRef.releaseTransitionViewRef(KEY_MAIN)
        if (transferee != null) {
            transferee!!.destroy()
        }
    }

    // 数据提供者 一次加载 or 分页
//    private fun myDataProvider(clickedData: PicData): DataProvider {
//        return if (loadAllAtOnce) {
//            provideViewerDataProvider { list }
//        } else {
//            provideViewerDataProvider(
//                loadInitial = { listOf(clickedData) },
//                loadAfter = { id, callback -> Api.asyncQueryAfter(id, callback) },
//                loadBefore = { id, callback -> Api.asyncQueryBefore(id, callback) }
//            )
//        }
//    }

    private val image = arrayOf(
        // gif
//        "https://img.nmgfic.com:90/uploadimg/image/20190305/15517786485c7e43584732a4.11936910.gif",
        // normal
        "https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/145939/pexels-photo-145939.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
        "https://images.unsplash.com/photo-1503066211613-c17ebc9daef0?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
        "https://images.unsplash.com/photo-1520848315518-b991dd16a625?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
        "https://images.unsplash.com/photo-1539418561314-565804e349c0?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
        "https://images.unsplash.com/photo-1539418561314-565804e349c0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1524272332618-3a94122bb0c1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU3NTIxfQ&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1524293191286-59ec719556d4?ixlib=rb-1.2.1&auto=format&fit=crop&w=654&q=80",
        "https://images.unsplash.com/photo-1478005344131-44da2ded3415?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1484406566174-9da000fda645?ixlib=rb-1.2.1&auto=format&fit=crop&w=635&q=80",
        "https://images.unsplash.com/photo-1462953491269-9aff00919695?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1543183344-acd290d5142e?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1452001603782-7d4e7d931173?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1539692858702-5cc9e1e40c17?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1563409236340-c174b51cbb81?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1486723312829-f32b4a25211b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1486518714050-b97edb7fcfa9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1554226114-f7ae1de16f55?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1550699566-83f93df24072?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1418405752269-40caf13f90ad?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1486365227551-f3f90034a57c?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1568435363428-2474799f37c3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjE3MzYxfQ&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1553338258-24fe91e8baf3?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1491604612772-6853927639ef?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1565416448218-e59ef8b4f03a?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1516728778615-2d590ea1855e?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1574260288371-7b63f7e3f186?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1550684863-a70a48d476d5?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1496963729609-7d408fa580b5?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1531959870249-9f9b729efcf4?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
    )
}