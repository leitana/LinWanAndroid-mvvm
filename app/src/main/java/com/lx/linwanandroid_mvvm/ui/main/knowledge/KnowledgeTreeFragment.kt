package com.lx.linwanandroid_mvvm.ui.main.knowledge

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMFragment
import com.lx.linwanandroid_mvvm.databinding.FragmentKnowledgeTreeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：KnowledgeTreeFragment
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/05
 */
class KnowledgeTreeFragment: BaseVMFragment<FragmentKnowledgeTreeBinding>(R.layout.fragment_knowledge_tree) {
    private val knowledgeTreeViewModel by viewModel<KnowledgeTreeViewModel>()
    private val knowledgeTreeAdapter: KnowledgeTreeAdapter by lazy { KnowledgeTreeAdapter() }

    override fun initView() {
        binding.run {
            adapter = knowledgeTreeAdapter
            viewModel = knowledgeTreeViewModel
            multipleStatusView.showContent()
        }
    }

    override fun initData() {
        knowledgeTreeViewModel.getDataList()
    }

    override fun startObserve() {
        knowledgeTreeViewModel.run {
            treeDatas.observe(this@KnowledgeTreeFragment, {
                knowledgeTreeAdapter.setList(it)
            })
        }
    }
}