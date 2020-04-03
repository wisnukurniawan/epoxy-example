package com.wisnu.epoxyexample.feature.home.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiState
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by currentScope.viewModel(this)
    private val homeItemController: HomeItemController by lazy { HomeItemController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Init view
        home_rv.layoutManager = LinearLayoutManager(this)
        home_rv.setItemSpacingDp(8)
        home_rv.setController(homeItemController)

        // Load data
        homeViewModel.loadContent()

        // Listen state
        homeViewModel.state.observe(this,
            Observer { state ->
                when (state) {
                    is HomeUiState.ShowContent -> showContent()
                    is HomeUiState.ShowLoading -> showLoading()
                    is HomeUiState.ShowError -> showError()
                    is HomeUiState.ProfileResult -> {
                        homeItemController.setProfile(state.model)
                    }
                    is HomeUiState.KotlinProjectResult -> {
                        homeItemController.setKotlinProjects(state.model.toMutableList())
                    }
                    is HomeUiState.JavaProjectResult -> {
                        homeItemController.setJavaProjects(state.model.toMutableList())
                    }
                    is HomeUiState.ProjectResult -> {
                        homeItemController.setProjects(state.model.toMutableList())
                    }
                }
            }
        )
    }

    private fun showLoading() {
        home_rv.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    private fun showContent() {
        home_rv.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
    }

    private fun showError() {
        home_rv.visibility = View.GONE
        loading_view.visibility = View.GONE
    }

}
