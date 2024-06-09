package com.lollipop.photo.wallpaper.engine

import android.graphics.Canvas
import android.graphics.Rect

abstract class WallpaperDrawable {

    protected val bounds = Rect()

    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    fun invalidateSelf() {
        callback?.invalidateDrawable(this)
    }

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        bounds.set(left, top, right, bottom)
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