package com.wisnu.epoxyexample.feature.home.ui

import android.content.Context
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.wisnu.epoxyexample.feature.home.ui.model.HomeItemModel
import com.wisnu.epoxyexample.feature.home.ui.view.*


class HomeItemController(private val context: Context) : EpoxyController() {

  @AutoModel
  lateinit var loadMoreView: LoadMoreView_

  var hasMoreToLoad = true
  val data: MutableList<HomeItemModel> = mutableListOf()

  fun setData(data: MutableList<HomeItemModel>) {
    this.data.clear()
    this.data.addAll(data)
    requestModelBuild()
  }

  fun addData(data: MutableList<HomeItemModel>) {
    this.data.addAll(data)
    requestModelBuild()
  }

  override fun buildModels() {
    this.data.forEach {
      when (it) {
        is ProfileView.Model -> {
          profileView(context) {
            id(it.id)
            model(it)
          }
        }
        is ProjectView.Model -> {
          projectView(context) {
            id(it.id)
            model(it)
          }
        }
      }
    }

    loadMoreView.addIf(hasMoreToLoad, this)

  }

}