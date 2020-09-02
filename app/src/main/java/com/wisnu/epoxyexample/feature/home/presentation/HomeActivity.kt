package com.wisnu.epoxyexample.feature.home.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.domain.handleEffect
import com.wisnu.epoxyexample.feature.home.domain.init
import com.wisnu.epoxyexample.feature.home.presentation.model.HomeEvent
import com.wisnu.epoxyexample.feature.home.presentation.model.HomeModel
import com.wisnu.epoxyexample.feature.home.presentation.model.LoadDataState
import com.wisnu.epoxyexample.feature.home.domain.update
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_home.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by currentScope.viewModel(this)
    private val homeItemController: HomeItemController by lazy {
        HomeItemController(
            this
        )
    }
    private lateinit var controller: MobiusLoop.Controller<HomeModel, HomeEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Init view
        home_rv.layoutManager = LinearLayoutManager(this)
        home_rv.setItemSpacingDp(8)
        home_rv.setController(homeItemController)

//        // Load data
//        homeViewModel.loadContent()
//
//        // Listen state
//        homeViewModel.state.observe(this,
//            Observer { state ->
//                when (state) {
//                    is HomeUiState.ShowContent -> showContent()
//                    is HomeUiState.ShowLoading -> showLoading()
//                    is HomeUiState.ShowError -> showError()
//                    is HomeUiState.ProfileResult -> {
//                        homeItemController.setProfile(state.model)
//                    }
//                    is HomeUiState.KotlinProjectResult -> {
//                        homeItemController.setKotlinProjects(state.model.toMutableList())
//                    }
//                    is HomeUiState.JavaProjectResult -> {
//                        homeItemController.setJavaProjects(state.model.toMutableList())
//                    }
//                    is HomeUiState.ProjectResult -> {
//                        homeItemController.setProjects(state.model.toMutableList())
//                    }
//                }
//            }
//        )

        // Mobius loop
        val loop = RxMobius.loop(
            // State update event handler
            Update { model: HomeModel, event: HomeEvent -> update(model, event) },
            // State effect event handler
            handleEffect(homeViewModel.homeUseCase)
        )
            .logger(AndroidLogger("Home404"))

        // Mobius controller
        controller = MobiusAndroid.controller(
            loop,
            // Initial
            HomeModel.initial(),
            // Initial side effect
            { model -> init(model) }
        )

        controller.connect(RxConnectables.fromTransformer { modelObservable ->
            // Mobius pub sub event
            render(modelObservable)
        })
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        controller.stop()
        super.onPause()
    }

    override fun onDestroy() {
        controller.disconnect()
        super.onDestroy()
    }

    private fun render(model: Observable<HomeModel>): Observable<HomeEvent> {
        val disposables = CompositeDisposable()

        disposables.add(
            model.subscribe {
                when (it.loadDataState) {
                    is LoadDataState.WaitingForData -> showLoading()
                    is LoadDataState.Loaded -> {
                        homeItemController.setProfile(it.loadDataState.myProfileModel)
                        homeItemController.setProjects(it.loadDataState.myProjectModels.toMutableList())
                        homeItemController.setKotlinProjects(it.loadDataState.kotlinProjectModels.toMutableList())
                        homeItemController.setJavaProjects(it.loadDataState.javaProjectModels.toMutableList())
                        showContent()
                    }
                    is LoadDataState.Failed -> showError()
                    is LoadDataState.NoResult -> showError()
                }
            }
        )

        return Observable.mergeArray(
            RxView.clicks(home_toolbar_tv).map { HomeEvent.RefreshDataRequested as HomeEvent }
        ).doOnDispose { disposables.clear() }
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
