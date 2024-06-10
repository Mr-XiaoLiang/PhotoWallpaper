package com.lollipop.photo.wallpaper.engine

import kotlin.math.max
import kotlin.math.min


interface LBounds {

    val outerLeft: Float
    val outerTop: Float
    val outerRight: Float
    val outerBottom: Float

    val innerLeft: Float
    val innerTop: Float
    val innerRight: Float
    val innerBottom: Float

    fun outerWidth(): Float {
        return outerRight - outerLeft
    }

    fun outerHeight(): Float {
        return outerBottom - outerTop
    }

    fun innerWidth(): Float {
        return innerRight - innerLeft
    }

    fun innerHeight(): Float {
        return innerBottom - innerTop
    }

    fun outerCenterX(): Float {
        return (outerLeft + outerRight) * 0.5F
    }

    fun outerCenterY(): Float {
        return (outerTop + outerBottom) * 0.5F
    }

    fun innerCenterX(): Float {
        return (innerLeft + innerRight) * 0.5F
    }

    fun innerCenterY(): Float {
        return (innerTop + innerBottom) * 0.5F
    }

}

class LBoundsImpl : LBounds {

    override var outerLeft = 0f
    override var outerTop = 0f
    override var outerRight = 0f
    override var outerBottom = 0f

    override var innerLeft = 0f
    override var innerTop = 0f
    override var innerRight = 0f
    override var innerBottom = 0f

    fun setQuadrilateral(
        leftTopX: Float,
        leftTopY: Float,
        rightTopX: Float,
        rightTopY: Float,
        leftBottomX: Float,
        leftBottomY: Float,
        rightBottomX: Float,
        rightBottomY: Float
    ) {
        outerLeft = min(leftTopX, leftBottomX)
        innerLeft = max(leftTopX, leftBottomX)

        outerTop = min(leftTopY, rightTopY)
        innerTop = max(leftTopY, rightTopY)

        outerRight = max(rightTopX, rightBottomX)
        innerRight = min(rightTopX, rightBottomX)

        outerBottom = max(leftBottomY, rightBottomY)
        innerBottom = min(leftBottomY, rightBottomY)
    }

    fun setBounds(
        left: Float, top: Float, right: Float, bottom: Float,
        insetLeft: Float, insetTop: Float, insetRight: Float, insetBottom: Float
    ) {
        outerLeft = left
        outerTop = top
        outerRight = right
        outerBottom = bottom
        innerLeft = left + insetLeft
        innerTop = top + insetTop
        innerRight = right - insetRight
        innerBottom = bottom - insetBottom
    }

    fun copyFrom(other: LBounds) {
        outerLeft = other.outerLeft
        outerTop = other.outerTop
        outerRight = other.outerRight
        outerBottom = other.outerBottom
        innerLeft = other.innerLeft
        innerTop = other.innerTop
        innerRight = other.innerRight
        innerBottom = other.innerBottom
    }

}