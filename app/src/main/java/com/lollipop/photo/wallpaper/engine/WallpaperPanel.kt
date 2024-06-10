package com.lollipop.photo.wallpaper.engine

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path

abstract class WallpaperPanel : WallpaperDrawable() {

    private val tileList = ArrayList<TileInfo>()

    val tileCount: Int
        get() {
            return tileList.size
        }

    fun getTileAt(index: Int): WallpaperTile? {
        if (index < 0 || index >= tileCount) {
            return null
        }
        return tileList[index].tile
    }

    fun getTileBoundsAt(index: Int): TileBounds? {
        if (index < 0 || index >= tileCount) {
            return null
        }
        return tileList[index].bounds
    }

    override fun onCreate() {
        super.onCreate()
        val tiles = createTiles()
        tiles.forEach {
            tileList.add(TileInfo(it))
            it.onCreate()
        }
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
        for (info in tileList) {
            val saveCount = canvas.save()
            val tileBounds = info.bounds
            // 偏移到磁块位置
            canvas.translate(tileBounds.outerLeft, tileBounds.outerTop)
            // 剪裁到磁块大小
            canvas.clipPath(info.clipPath)
            // 绘制
            info.tile.draw(canvas)
            // 恢复
            canvas.restoreToCount(saveCount)
        }
    }

    override fun onBoundsChanged() {
        super.onBoundsChanged()
        layoutTiles()
        val tempBounds = LBoundsImpl()
        for (info in tileList) {
            tempBounds.setQuadrilateral(
                info.bounds.leftTopX,
                info.bounds.leftTopY,
                info.bounds.rightTopX,
                info.bounds.rightTopY,
                info.bounds.leftBottomX,
                info.bounds.leftBottomY,
                info.bounds.rightBottomX,
                info.bounds.rightBottomY
            )
            tempBounds.offset(info.bounds.outerLeft * -1, info.bounds.outerTop * -1)
            updateTileClipPath(info)
            info.tile.setBounds(tempBounds)
        }
    }

    private fun updateTileClipPath(info: TileInfo) {
        val clipPath = info.clipPath
        val tb = info.bounds
        clipPath.reset()
        val offsetX = tb.outerLeft * -1
        val offsetY = tb.outerTop * -1
        clipPath.moveTo(tb.leftTopX, tb.leftTopY)
        clipPath.lineTo(tb.rightTopX, tb.rightTopY)
        clipPath.lineTo(tb.rightBottomX, tb.rightBottomY)
        clipPath.lineTo(tb.leftBottomX, tb.leftBottomY)
        clipPath.close()
        clipPath.offset(offsetX, offsetY)
    }

    abstract fun createTiles(): List<WallpaperTile>

    protected abstract fun layoutTiles()

    private class TileInfo(
        val tile: WallpaperTile,
    ) {
        val clipPath: Path = Path()
        val bounds: TileBounds = TileBounds()
    }

}