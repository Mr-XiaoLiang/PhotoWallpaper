package com.lollipop.photo.wallpaper.engine

import android.os.Handler
import android.os.HandlerThread

class EngineLooper {

    private val thread by lazy {
        val impl = HandlerThread("EngineLooper")
        impl.start()
        impl
    }

    private val handler by lazy {
        Handler(thread.looper)
    }

    var isAlive: Boolean = true
        private set

    fun post(runnable: Runnable) {
        if (!isAlive) {
            return
        }
        handler.post(runnable)
    }

    fun postDelay(runnable: Runnable, delay: Long) {
        if (!isAlive) {
            return
        }
        handler.postDelayed(runnable, delay)
    }

    fun remove(runnable: Runnable) {
        if (!isAlive) {
            return
        }
        handler.removeCallbacks(runnable)
    }

    fun destroy() {
        isAlive = false
        thread.quitSafely()
    }

}