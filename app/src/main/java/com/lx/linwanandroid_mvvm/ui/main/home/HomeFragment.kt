package com.lx.linwanandroid_mvvm.ui.main.home

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMFragment
import com.lx.linwanandroid_mvvm.databinding.FragmentHomeLayoutBinding
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

    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(requireContext(), mutableListOf()) }

    override fun initView() {
        binding.run {
            adapter = homeAdapter
        }
    }

    override fun initData() {
        homeViewModel.getArticles(1)
    }

    override fun startObserve() {
        homeViewModel.run {
            homeList.observe(this@HomeFragment, {
                homeAdapter.setData(it)
            })
            uiState.observe(this@HomeFragment, {
                when(it) {
                    HomeViewModel.StateView.SUCCESS -> binding.multipleStatusView.showContent()
                    HomeViewModel.StateView.ERROR -> binding.multipleStatusView.showError()
                    HomeViewModel.StateView.EMPTY -> binding.multipleStatusView.showEmpty()
                    HomeViewModel.StateView.LODING -> binding.multipleStatusView.showLoading()
                    HomeViewModel.StateView.NETWORK_ERROR -> binding.multipleStatusView.showNoNetwork()
                }
            })
        }
    }
}