package com.wisnu.epoxyexample.feature.home.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.HomeItemModel
import com.wisnu.epoxyexample.feature.home.ui.model.HomeState
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

  private val homeViewModel: HomeViewModel by currentScope.viewModel(this)
  private lateinit var homeItemController: HomeItemController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    homeItemController = HomeItemController(this)
    home_rv.setController(homeItemController)
    home_rv.setItemSpacingDp(16)

    homeViewModel.loadProfile()
    homeViewModel.state.observe(this,
        Observer { state ->
          when (state) {
            is HomeState.ShowLoading -> showLoading()
            is HomeState.Error -> {}
            is HomeState.Result -> {
              hideLoading()
              bindData(state.list)
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

  private fun bindData(list: List<HomeItemModel>) {
    homeItemController.setData(list)
  }

}
