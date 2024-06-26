package com.lollipop.photo.wallpaper

import android.graphics.Color
import android.graphics.Paint
import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowInsets

class PhotoWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return PhotoWallpaperEngine(this)
    }

    private inner class PhotoWallpaperEngine(
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
            holder ?: return
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.RED)
            canvas.drawRect(0f, 0f, 500f, 500f, Paint().apply {
                color = Color.GRAY
                style = Paint.Style.FILL
            })
            holder.unlockCanvasAndPost(canvas)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
        }

    }

}