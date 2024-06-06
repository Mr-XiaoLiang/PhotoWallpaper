package com.lollipop.photo.wallpaper

import android.service.wallpaper.WallpaperService

class PhotoWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return PhotoWallpaperEngine(this)
    }

}