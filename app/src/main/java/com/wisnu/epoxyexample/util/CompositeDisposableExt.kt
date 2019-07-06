package com.wisnu.epoxyexample.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(other: Disposable): Unit = this.run { add(this) }