package com.selfdev.mp3player.screen

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object Scan: Screen("scan_screen")
}
