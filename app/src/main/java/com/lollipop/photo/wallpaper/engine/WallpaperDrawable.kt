package com.lollipop.photo.wallpaper.engine

import android.graphics.Canvas

abstract class WallpaperDrawable {

    val bounds = LBoundsImpl()

    private var callback: Callback? = null

    var isNight = false

    open fun onCreate() {

    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    fun invalidateSelf() {
        callback?.invalidateDrawable(this)
    }

    fun setBounds(source: LBounds) {
        bounds.copyFrom(source)
        onBoundsChanged()
    }

    open fun onBoundsChanged() {

    }

    abstract fun draw(canvas: Canvas)

    open fun onAnimationChanged(progress: Float, isClose: Boolean) {

    }

    interface Callback {
        fun invalidateDrawable(who: WallpaperDrawable)
    }

}