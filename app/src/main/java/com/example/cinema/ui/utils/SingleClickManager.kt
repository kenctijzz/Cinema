package com.example.cinema.ui.utils


object SingleClickManager {
    private var lastClickedTime = 0L
    private const val THROTTLE_DELAY = 300L
    fun performClick(onClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickedTime > THROTTLE_DELAY) {
            lastClickedTime = currentTime
            onClick()
        }
    }
}

