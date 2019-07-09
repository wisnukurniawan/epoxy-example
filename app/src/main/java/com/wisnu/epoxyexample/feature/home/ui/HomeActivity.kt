package com.wisnu.epoxyexample.feature.home.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.HomeState
import com.wisnu.epoxyexample.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

  private val homeViewModel: HomeViewModel by currentScope.viewModel(this)
  private lateinit var homeItemController: HomeItemController
  private lateinit var loadMoreScrollListener: EndlessRecyclerViewScrollListener

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    // Init object
    val layoutManager = LinearLayoutManager(this)
    loadMoreScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
      override fun onLoadMore(page: Int) {
        showLoadMore()
        homeViewModel.loadNextProjects(page + 1)
      }
    }
    homeItemController = HomeItemController(this)

    // Init view
    home_rv.layoutManager = layoutManager
    home_rv.setItemSpacingDp(16)
    home_rv.addOnScrollListener(loadMoreScrollListener)
    home_rv.setController(homeItemController)

    homeViewModel.loadProfile()

    // Listen state
    homeViewModel.state.observe(this,
        Observer { state ->
          when (state) {
            is HomeState.ShowLoading -> {
              showLoading()
            }
            is HomeState.Error -> {
              hideLoadMore()
            }
            is HomeState.Result -> {
              hideLoading()

              homeItemController.setData(state.list.toMutableList())
            }
            is HomeState.NextResult -> {
              if (state.list.isEmpty()) hideLoadMore()

              homeItemController.addData(state.list.toMutableList())
            }
          }
        }
    )
  }

  private fun showLoading() {
    loading_view.visibility = View.VISIBLE
  }

  private fun showLoadMore() {
    homeItemController.hasMoreToLoad = true
  }

  private fun hideLoading() {
    loading_view.visibility = View.GONE
  }

  private fun hideLoadMore() {
    homeItemController.hasMoreToLoad = false
  }

}
