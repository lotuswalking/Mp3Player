package com.selfdev.mp3player


import InsertAllAsyncTask
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.selfdev.mp3player.data.AudioDatabase
import com.selfdev.mp3player.data.AudioFile
import com.selfdev.mp3player.data.AudioFileDao
import com.selfdev.mp3player.Model.HomeViewModel
import com.selfdev.mp3player.data.AudioFileState
import com.selfdev.mp3player.data.ContentResolverHelper
import com.selfdev.mp3player.screen.Screen
import com.selfdev.mp3player.ui.theme.AudioFileRow
import java.util.Locale

@Composable
fun Navigation(
    state:AudioFileState,
    navController: NavHostController,
    context: Context,
    myView: HomeViewModel,
    modifier: Modifier = Modifier
) {
//    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(state = state,navController = navController, context, myView = myView)
        }
        composable(
            route = Screen.Scan.route,

            ) {
            DetailScreen(navController = navController, context = context)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: AudioFileState,
    navController: NavController, context: Context,myView: HomeViewModel) {
    var text by remember { mutableStateOf("") }
    val audioList = state.audioFiles
    println("there are ${audioList.size} Audio file in play List!")
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Scan.route)

            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "To Detail Screen")
        }

        AudioFileRow(state)


    }

}

@Composable
fun DetailScreen(navController: NavController, context: Context) {
    val uname = "default name"
    val otherName = "default Other Name"
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {

        Column {
            Text(
                text = "Hello: $uname",
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Your In put is : $otherName",
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
            )
            Row {
                Button(onClick = {
                    navController.navigate(Screen.Main.route)
                }) {
                    Text(text = "Back to Main")

                }
                Button(onClick = {
//                    val root = Environment.getExternalStorageDirectory()

//                    putAllFilesInDatabase(context)
                    ContentResolverHelper(context).searchAllAudios()

//                    println("there is ${fileAndFolders.size} file in root folder")
                }) {
                    Text(text = "Start To Scan")

                }
            }

        }

    }
}



@Composable
fun MyTabRow(
    allScreen: List<NavDestination>,
    currentScreen: NavDestination
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            AllScreen.forEach { screen ->
                Navbar(
                    text = screen.route,
                    icon = screen.icon,
                    selected = currentScreen == screen

                )

            }
        }
    }
}

@Composable
private fun Navbar(
    text: String,
    icon: ImageVector,
//    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.surface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
//            .selectable(
//                selected = selected,
//                onClick = onSelected,
//                role = Role.Tab,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = rememberRipple(
//                    bounded = false,
//                    radius = Dp.Unspecified,
//                    color = Color.Unspecified
//                )
//            )
//            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(imageVector = icon, contentDescription = text)
        if (selected) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(text.uppercase(Locale.getDefault()))
        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100