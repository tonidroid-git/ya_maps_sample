package com.tonib.yandexmapsample

import android.graphics.Color
import androidx.core.graphics.alpha

object ColorUtils {
    private var colorIndex = 0
    private var colors = listOf(
        Color.valueOf(0x53B6244F).toArgb(),
        Color.valueOf(0x55BB4A11).toArgb(),
        Color.valueOf(0x77BFADA3).toArgb(),
        Color.valueOf(0x505FD34B).toArgb()
    )

    /**
     * Returns a different color every time.
     */
    fun polylineColor(): Int {
        return colors[colorIndex].also {
            colorIndex = (colorIndex + 1) % colors.size
        }
    }
}
