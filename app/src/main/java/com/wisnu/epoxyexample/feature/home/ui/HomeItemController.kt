package com.wisnu.epoxyexample.feature.home.ui

import android.content.Context
import com.airbnb.epoxy.*
import com.wisnu.epoxyexample.feature.home.ui.model.ProfileUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProjectUiModel
import com.wisnu.epoxyexample.feature.home.ui.view.*

class HomeItemController(private val context: Context) : EpoxyController() {

    private var profile: ProfileUiModel? = null
    private val kotlinProjects: MutableList<ProjectUiModel> = mutableListOf()
    private val javaProjects: MutableList<ProjectUiModel> = mutableListOf()
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

    fun setKotlinProjects(data: MutableList<ProjectUiModel>) {
        this.kotlinProjects.clear()
        this.kotlinProjects.addAll(data)
        requestModelBuild()
    }

    fun setJavaProjects(data: MutableList<ProjectUiModel>) {
        this.javaProjects.clear()
        this.javaProjects.addAll(data)
        requestModelBuild()
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
            .id("carousel.projects")
            .models(
                this.projects.map {
                    ProjectView2_(context)
                        .id(it.id)
                        .model(it)
                }
            )
            .addIf(this.projects.isNotEmpty(), this)

        HeaderView_()
            .id("header.kotlin.trending.projects")
            .title("Kotlin Trending Repositories")
            .addIf(this.kotlinProjects.isNotEmpty(), this)

        CarouselModel_()
            .padding(Carousel.Padding.dp(16, 2, 16, 2, 8))
            .id("carousel.kotlin.trending.projects")
            .models(
                this.kotlinProjects.map {
                    ProjectView2_(context)
                        .id(it.id)
                        .model(it)
                }
            )
            .addIf(this.kotlinProjects.isNotEmpty(), this)

        HeaderView_()
            .id("header.java.trending.projects")
            .title("Java Trending Repositories")
            .addIf(this.javaProjects.isNotEmpty(), this)

        CarouselModel_()
            .padding(Carousel.Padding.dp(16, 2, 16, 2, 8))
            .id("carousel.java.trending.projects")
            .models(
                this.javaProjects.map {
                    ProjectView2_(context)
                        .id(it.id)
                        .model(it)
                }
            )
            .addIf(this.javaProjects.isNotEmpty(), this)
    }

}