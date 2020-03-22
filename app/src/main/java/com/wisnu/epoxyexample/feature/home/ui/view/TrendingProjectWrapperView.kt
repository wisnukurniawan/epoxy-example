package com.wisnu.epoxyexample.feature.home.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.airbnb.epoxy.*
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.TrendingProjectUiModel

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TrendingProjectWrapperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val carousel: Carousel

    init {
        inflate(context, R.layout.layout_trending_project_wrapper, this)
        carousel = findViewById(R.id.project_carousel)
        carousel.setPadding(Carousel.Padding.dp(16, 2, 16, 2, 8))
    }

    @ModelProp
    fun setTrendingProject(projects: List<TrendingProjectUiModel>) {
        val models = projects.map {
            TrendingProjectView_(context)
                .id(it.id)
                .model(it)
        }
        carousel.setModels(models)
    }
}