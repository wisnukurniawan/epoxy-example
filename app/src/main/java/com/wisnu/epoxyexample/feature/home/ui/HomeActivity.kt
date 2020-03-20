package com.wisnu.epoxyexample.feature.home.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiState
import com.wisnu.epoxyexample.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by currentScope.viewModel(this)
    private val homeItemController: HomeItemController by lazy { HomeItemController(this) }
    private val loadMoreScrollListener: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(home_rv.layoutManager!!) {
            override fun onLoadMore(page: Int) {
                showLoadMore()
                homeViewModel.loadNextProjects(page + 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Init view
        home_rv.layoutManager = LinearLayoutManager(this)
        home_rv.setItemSpacingDp(16)
        home_rv.addOnScrollListener(loadMoreScrollListener)
        home_rv.setController(homeItemController)

        homeViewModel.loadData()

        // Listen state
        homeViewModel.state.observe(this,
            Observer { state ->
                when (state) {
                    is HomeUiState.HideLoading -> hideLoading()
                    is HomeUiState.ShowLoading -> showLoading()
                    is HomeUiState.HideLoadMore -> hideLoadMore()
                    is HomeUiState.Error -> {
                    }
                    is HomeUiState.Result -> {
                        homeItemController.setData(state.list.toMutableList())
                    }
                    is HomeUiState.NextResult -> {
                        homeItemController.addData(state.list.toMutableList())
                    }
                }
            }
        )
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    private fun showLoadMore() {
        homeItemController.showLoadMore()
    }

    private fun hideLoadMore() {
        homeItemController.hideLoadMore()
    }

}
