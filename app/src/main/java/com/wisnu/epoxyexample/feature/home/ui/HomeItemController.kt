package com.wisnu.epoxyexample.feature.home.ui

import android.content.Context
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiItemModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProfileUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProjectUiModel
import com.wisnu.epoxyexample.feature.home.ui.view.*

class HomeItemController(private val context: Context) : EpoxyController() {

  @AutoModel
  lateinit var loadMoreView: LoadMoreView_

  private var shouldShowLoadMore = true
  private val data: MutableList<HomeUiItemModel> = mutableListOf()

  fun setData(data: MutableList<HomeUiItemModel>) {
    this.data.clear()
    this.data.addAll(data)
    requestModelBuild()
  }

  fun addData(data: MutableList<HomeUiItemModel>) {
    this.data.addAll(data)
    requestModelBuild()
  }

  fun showLoadMore() {
    shouldShowLoadMore = true
  }

  fun hideLoadMore() {
    shouldShowLoadMore = false
  }

  override fun buildModels() {
    this.data.forEach {
      when (it) {
        is ProfileUiModel -> {
          profileView(context) {
            id(it.id)
            model(it)
          }
        }
        is ProjectUiModel -> {
          projectView(context) {
            id(it.id)
            model(it)
          }
        }
      }
    }

    loadMoreView.addIf(shouldShowLoadMore, this)

  }

}