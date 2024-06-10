package com.lollipop.photo.wallpaper.engine

import android.graphics.Canvas
import android.graphics.Color

abstract class WallpaperPanel : WallpaperDrawable() {

    private val tileList = mutableListOf<WallpaperTile>()

    override fun onCreate() {
        super.onCreate()
        val tiles = createTiles()
        tileList.addAll(tiles)
        tiles.forEach { it.onCreate() }
    }

    override fun draw(canvas: Canvas) {
        drawBackground(canvas)
        drawTiles(canvas)
    }

    protected open fun drawBackground(canvas: Canvas) {
        canvas.drawColor(
            if (isNight) {
                Color.BLACK
            } else {
                Color.WHITE
            }
        )
    }

    protected open fun drawTiles(canvas: Canvas) {
        for (tile in tileList) {
            tile.draw(canvas)
        }
    }

    override fun onBoundsChanged() {
        super.onBoundsChanged()
        layoutTiles()
    }

    abstract fun createTiles(): List<WallpaperTile>

    protected abstract fun layoutTiles()

}