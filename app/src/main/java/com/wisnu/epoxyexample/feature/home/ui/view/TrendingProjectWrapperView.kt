package com.wisnu.epoxyexample.feature.home.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.airbnb.epoxy.*
import com.wisnu.epoxyexample.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TrendingProjectWrapperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val carousel: EpoxyRecyclerView

    init {
        inflate(context, R.layout.layout_trending_project_wrapper, this)
        carousel = findViewById(R.id.project_carousel)
    }

    @ModelProp
    fun setTrendingProject(trendingProjectCarousel: CarouselModel_) {
        carousel.setModels(listOf(trendingProjectCarousel))
    }
}