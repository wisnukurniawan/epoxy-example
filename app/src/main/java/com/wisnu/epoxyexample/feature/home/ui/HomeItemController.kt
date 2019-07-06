package com.wisnu.epoxyexample.feature.home.ui

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.wisnu.epoxyexample.feature.home.ui.model.HomeItemModel
import com.wisnu.epoxyexample.feature.home.ui.view.ProfileView
import com.wisnu.epoxyexample.feature.home.ui.view.ProjectView
import com.wisnu.epoxyexample.feature.home.ui.view.profileView
import com.wisnu.epoxyexample.feature.home.ui.view.projectView

class HomeItemController(private val context: Context) : TypedEpoxyController<List<HomeItemModel>>() {

  override fun buildModels(data: List<HomeItemModel>?) {
    data?.forEach {
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
  }

}