package com.selfdev.mp3player

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable

interface NavDestination {
    val icon: ImageVector
    val route: String
}

object Main: NavDestination {
    override val icon = Icons.Filled.Home
    override val route = "Main"
}
object Detail: NavDestination {
    override val icon = Icons.Filled.Search
    override val route = "Detail"
}
val AllScreen = listOf(Main,Detail)