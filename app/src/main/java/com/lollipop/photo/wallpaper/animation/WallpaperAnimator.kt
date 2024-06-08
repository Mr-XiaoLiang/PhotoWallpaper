package com.lollipop.photo.wallpaper.animation

import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.lollipop.photo.wallpaper.engine.EngineLooper
import kotlin.math.max

class WallpaperAnimator(
    val looper: EngineLooper,
    val onUpdateCallback: OnUpdateCallback
) {

    var state: State = State.IDLE
        private set

    var fromValue: Float = 0f
        private set
    var toValue: Float = 1f
        private set

    var duration: Long = 300
        private set

    var repeatCount = 1
        private set

    val isRunning: Boolean
        get() {
            return state == State.RUNNING
        }

    var repeatIndex = 0
        private set

    var repeatMode: RepeatMode = RepeatMode.RESTART
        private set

    var interpolator: Interpolator = LinearInterpolator()
        private set

    private var startTime: Long = 0

    private val updateTask = UpdateRunnable(::next)

    private var stateChangeCallback: OnStateChangeCallback? = null

    fun setValue(from: Float, to: Float) {
        if (isRunning) {
            return
        }
        fromValue = from
        toValue = to
    }

    fun setInterpolator(interpolator: Interpolator) {
        if (isRunning) {
            return
        }
        this.interpolator = interpolator
    }

    fun setDuration(duration: Long) {
        if (isRunning) {
            return
        }
        this.duration = duration
    }

    fun setRepeatCount(repeatCount: Int) {
        if (isRunning) {
            return
        }
        this.repeatCount = repeatCount
    }

    fun setRepeatMode(repeatMode: RepeatMode) {
        if (isRunning) {
            return
        }
        this.repeatMode = repeatMode
    }

    private fun now(): Long {
        // 通过启动的时间来计算，避免出现修改时间戳导致的计算错误
        return SystemClock.elapsedRealtime()
    }

    private fun next() {
        val now = now()
        val d = max(0L, now - startTime)
        if (d >= duration) {
            // 越界了，我们就直接使用结果好了
            updateProgress(1F)
            // 循环次数累加，循环的次数表示从开始的真实次数，不受到模式的影响
            repeatIndex++
            // 如果循环次数是小于1的，那么我们认为是死循环，否则我们在有限的次数内循环
            if (repeatCount < 1 || repeatIndex < repeatCount) {
                // 重新开始循环
                state = State.RUNNING
                stateChangeCallback?.onStateChange(this, Event.REPEAT)
                when (repeatMode) {
                    RepeatMode.RESTART -> {
                        // 我们是基于时间的动画进度计算，所以我们只需要重新设置开始的时间，进度就会被重置
                        startTime = now()
                    }

                    RepeatMode.REVERSAL -> {
                        // 如果需要反转，那么除了时间会被重置，我们的开始与结束的值，也需要交换
                        startTime = now()
                        val from = fromValue
                        fromValue = toValue
                        toValue = from
                    }
                }
            } else {
                // 如果不循环，那么就触发结束事件
                state = State.IDLE
                stateChangeCallback?.onStateChange(this, Event.END)
            }
        } else {
            updateProgress(d.toFloat() / duration)
        }
        postNext()
    }

    private fun updateProgress(progress: Float) {
        // 如果进度在范围内，那么就更新进度
        // 首先计算时间维度上的进度值，然后在0~1的范围内，直接扔给插值器过一遍
        val realProgress = interpolator.getInterpolation(progress)
        // 拿到插值之后的进度条，计算开始到结束之间的实际值
        val currentValue = (toValue - fromValue) * realProgress + fromValue
        // 更新一波
        onUpdateCallback.onUpdate(this, currentValue)
    }

    fun start() {
        state = State.RUNNING
        startTime = now()
        repeatIndex = 0
        postNext()
        stateChangeCallback?.onStateChange(this, Event.START)
    }

    fun cancel() {
        state = State.IDLE
        looper.remove(updateTask)
        stateChangeCallback?.onStateChange(this, Event.CANCEL)
    }

    private fun postNext() {
        if (state == State.RUNNING) {
            looper.post(updateTask)
        }
    }

    enum class State {
        IDLE,
        RUNNING
    }

    enum class Event {
        START,
        END,
        CANCEL,
        REPEAT
    }

    enum class RepeatMode {
        RESTART,
        REVERSAL
    }

    fun interface OnUpdateCallback {
        fun onUpdate(animator: WallpaperAnimator, progress: Float)
    }

    fun interface OnStateChangeCallback {
        fun onStateChange(animator: WallpaperAnimator, event: Event)
    }

    private class UpdateRunnable(
        val onInvoke: () -> Unit
    ) : Runnable {
        override fun run() {
            onInvoke()
        }

    }

}