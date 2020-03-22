package com.wisnu.epoxyexample.base

sealed class UiModel<in T> {
    class Loading<T> : UiModel<T>()
    data class Data<T>(val data: T) : UiModel<T>()
    data class Error<T>(val throwable: Throwable) : UiModel<T>()
}