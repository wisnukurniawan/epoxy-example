package com.wisnu.epoxyexample.feature.home.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.*
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.presentation.model.ProjectUiModel
import kotlinx.android.synthetic.main.layout_project_container.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ProjectContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_project_container, this)
        project_carousel.setPadding(Carousel.Padding.dp(16, 2, 16, 2, 8))
    }

    @ModelProp
    fun setTrendingProject(projects: List<ProjectUiModel>) {
        val models = projects.map {
            ProjectView2_(context)
                .id(it.id)
                .model(it)
        }
        project_carousel.setModels(models)
    }
}