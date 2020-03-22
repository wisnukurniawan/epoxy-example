package com.wisnu.epoxyexample.feature.home.ui

import android.content.Context
import com.airbnb.epoxy.*
import com.wisnu.epoxyexample.feature.home.ui.model.ProfileUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProjectUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.TrendingProjectUiModel
import com.wisnu.epoxyexample.feature.home.ui.view.*

class HomeItemController(private val context: Context) : EpoxyController() {

    @AutoModel
    lateinit var loadMoreView: LoadMoreView_

    private var shouldShowLoadMore = false
    private var profile: ProfileUiModel? = null
    private var trendingProjects: MutableList<TrendingProjectUiModel> = mutableListOf()
    private val projects: MutableList<ProjectUiModel> = mutableListOf()

    fun setProfile(profile: ProfileUiModel) {
        this.profile = profile
        requestModelBuild()
    }

    fun setProjects(data: MutableList<ProjectUiModel>) {
        this.projects.clear()
        this.projects.addAll(data)
        requestModelBuild()
    }

    fun setTrendingProjects(data: MutableList<TrendingProjectUiModel>) {
        this.trendingProjects.clear()
        this.trendingProjects.addAll(data)
        requestModelBuild()
    }

    fun addProjects(data: MutableList<ProjectUiModel>) {
        this.projects.addAll(data)
        requestModelBuild()
    }

    fun showLoadMore() {
        shouldShowLoadMore = true
    }

    fun hideLoadMore() {
        shouldShowLoadMore = false
    }

    override fun buildModels() {
        this.profile?.let {
            ProfileView_(context)
                .id(it.id)
                .model(it)
                .addTo(this)
        }

        HeaderView_()
            .id("header.my.projects")
            .title("My Repositories")
            .addIf(this.projects.isNotEmpty(), this)

        CarouselModel_()
            .padding(Carousel.Padding.dp(16, 2, 16, 2, 8))
            .id("carousel.trending.projects")
            .models(
                this.projects.map {
                    ProjectView_(context)
                        .id(it.id)
                        .model(it)
                }
            )
            .addIf(this.projects.isNotEmpty(), this)

        HeaderView_()
            .id("header.trending.projects")
            .title("Kotlin Trending Repositories")
            .addIf(this.projects.isNotEmpty(), this)

        trendingProjects.map { model ->
            TrendingProjectView_(context)
                .id(model.id)
                .model(model)
                .addTo(this)
        }

        loadMoreView.addIf(shouldShowLoadMore, this)

    }

}