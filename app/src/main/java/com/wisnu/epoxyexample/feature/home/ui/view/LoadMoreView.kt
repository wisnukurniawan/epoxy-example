package com.wisnu.epoxyexample.feature.home.ui.view

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.wisnu.epoxyexample.R

@EpoxyModelClass(layout = R.layout.layout_home_loading_item)
abstract class LoadMoreView : EpoxyModel<View>()