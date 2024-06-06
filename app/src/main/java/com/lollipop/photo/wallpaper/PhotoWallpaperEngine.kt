package com.lollipop.photo.wallpaper

import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowInsets

class PhotoWallpaperEngine(
    private val service: PhotoWallpaperService
) : WallpaperService.Engine() {

    override fun onCreate(surfaceHolder: SurfaceHolder) {
        super.onCreate(surfaceHolder)
    }

    override fun onOffsetsChanged(
        xOffset: Float,
        yOffset: Float,
        xOffsetStep: Float,
        yOffsetStep: Float,
        xPixelOffset: Int,
        yPixelOffset: Int
    ) {
        super.onOffsetsChanged(
            xOffset,
            yOffset,
            xOffsetStep,
            yOffsetStep,
            xPixelOffset,
            yPixelOffset
        )
    }

    override fun onTouchEvent(event: MotionEvent?) {
        super.onTouchEvent(event)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?) {
        super.onApplyWindowInsets(insets)
    }

    override fun onVisibilityChanged(visible: Boolean) {
        super.onVisibilityChanged(visible)
    }

    override fun onSurfaceCreated(holder: SurfaceHolder?) {
        super.onSurfaceCreated(holder)
    }

    override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        super.onSurfaceChanged(holder, format, width, height)
    }

    override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
        super.onSurfaceDestroyed(holder)
    }

}