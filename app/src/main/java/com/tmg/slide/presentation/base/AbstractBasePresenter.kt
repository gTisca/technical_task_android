/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbstractBasePresenter<T : Any> internal constructor(
    internal val compositeDisposable: CompositeDisposable = CompositeDisposable()
) {

    constructor() : this(CompositeDisposable())

    private var view: T? = null

    fun bind(view: T) {
        check(this.view == null) { "view already bound" }
        this.view = view
        afterBind()
    }

    fun unbind() {
        checkNotNull(this.view) { "view already unbound" }
        this.view = null
        compositeDisposable.clear()
    }

    abstract fun afterBind()

    protected fun addDisposable(disposable: Disposable): Disposable =
        disposable.also {
            compositeDisposable.add(it)
        }

    fun getView(): T = view ?: throw IllegalStateException("getView not bound")
}