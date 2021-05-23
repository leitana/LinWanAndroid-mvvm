package com.lx.linwanandroid_mvvm.ui.main.home

import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import cn.bingoogolapple.bgabanner.BGABanner
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMFragment
import com.lx.linwanandroid_mvvm.databinding.FragmentHomeLayoutBinding
import com.lx.linwanandroid_mvvm.databinding.ItemHomeBannerBinding
import com.lx.linwanandroid_mvvm.ext.setLoadMoreStatus
import com.lx.linwanandroid_mvvm.utils.ImageLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：HomeFragment
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/13
 */
class HomeFragment: BaseVMFragment<FragmentHomeLayoutBinding>(R.layout.fragment_home_layout) {

    private val homeViewModel by viewModel<HomeViewModel>()

//    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(requireContext(), mutableListOf()) }
    private val homeAdapter: HomeAdapter2 by lazy { HomeAdapter2() }

    private val bannerView by lazy {
        layoutInflater.inflate(R.layout.item_home_banner, null)
    }

    private val bannerBinding by lazy {
        DataBindingUtil.bind<ItemHomeBannerBinding>(bannerView)
    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.load(context, feedImageUrl, imageView)
        }
    }

    override fun initView() {
        binding.run {
            adapter = homeAdapter
            viewModel = homeViewModel
            multipleStatusView.showContent()
        }

        homeAdapter.run {
            loadMoreModule.isEnableLoadMoreIfNotFullPage = false
            loadMoreModule.setOnLoadMoreListener { loadMore() }
            addHeaderView(bannerView)
        }

    }

    override fun initData() {
        loadBanner()
        refresh()
    }

    override fun startObserve() {
        homeViewModel.run {
            homeList.observe(this@HomeFragment, {
                homeAdapter.setList(it)
            })

            uiState.observe(this@HomeFragment, {
                when(it) {
//                    HomeViewModel.StateView.SUCCESS -> {
//                        binding.multipleStatusView.showContent()
//                    }
                    HomeViewModel.StateView.ERROR -> {
                        binding.multipleStatusView.showError()
                    }
                    HomeViewModel.StateView.EMPTY -> binding.multipleStatusView.showEmpty()
//                    HomeViewModel.StateView.LODING -> binding.multipleStatusView.showLoading()
                    HomeViewModel.StateView.NETWORK_ERROR -> binding.multipleStatusView.showNoNetwork()
                }
            })

            bannerList.observe(viewLifecycleOwner, {
                bannerBinding?.banner?.run {
                    val bannerFeedList = ArrayList<String>()
                    val bannerTitleList = ArrayList<String>()
                    for (banner in it) {
                        banner.imagePath?.let { bannerFeedList.add(it) }
                        banner.title?.let { bannerTitleList.add(it) }
                    }
                    setAutoPlayAble(bannerFeedList.size > 1)
                    setData(bannerFeedList, bannerTitleList)
                    setAdapter(bannerAdapter)
                }
            })

            refreshStatus.observe(this@HomeFragment, {
                binding.swipeRefreshLayout.isRefreshing = it
            })

            loadMoreStatus.observe(this@HomeFragment, {
                homeAdapter.loadMoreModule.setLoadMoreStatus(it)
            })
        }
    }

    private fun refresh(){
        homeAdapter.loadMoreModule.isEnableLoadMore = false
        homeViewModel.refreshList()
    }

    private fun loadMore(){
        homeViewModel.loadMoreList()
    }

    private fun loadBanner() {
        homeViewModel.loadBanner()
    }
}