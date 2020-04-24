package com.wisnu.epoxyexample.feature.home.domain.model

sealed class HomeEffect {

    data class LoadProfile(val profileState: LoadProfileState) : HomeEffect()
    data class LoadProject(val loadProjectState: LoadProjectState) : HomeEffect()
    data class LoadKotlinProject(val loadKotlinProjectState: LoadKotlinProjectState) : HomeEffect()
    data class LoadJavaProject(val loadJavaProjectState: LoadJavaProjectState) : HomeEffect()

}