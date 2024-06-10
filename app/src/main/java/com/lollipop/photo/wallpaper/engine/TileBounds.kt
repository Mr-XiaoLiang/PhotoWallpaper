package com.lollipop.photo.wallpaper.engine

import kotlin.math.max
import kotlin.math.min

class TileBounds {

    var leftTopX: Float = 0F
    var leftTopY: Float = 0F
    var rightTopX: Float = 0F
    var rightTopY: Float = 0F
    var leftBottomX: Float = 0F
    var leftBottomY: Float = 0F
    var rightBottomX: Float = 0F
    var rightBottomY: Float = 0F

    val outerLeft: Float
        get() {
            return min(leftTopX, leftBottomX)
        }

    val outerTop: Float
        get() {
            return min(leftTopY, rightTopY)
        }

    val outerRight: Float
        get() {
            return max(rightTopX, rightBottomX)
        }

    val outerBottom: Float
        get() {
            return max(leftBottomY, rightBottomY)
        }

    val outerWidth: Float
        get() {
            return outerRight - outerLeft
        }

    val outerHeight: Float
        get() {
            return outerBottom - outerTop
        }

}