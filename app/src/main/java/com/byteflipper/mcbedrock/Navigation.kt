package com.byteflipper.mcbedrock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.byteflipper.mcbedrock.screens.ChangelogesScreen
import com.byteflipper.mcbedrock.screens.HomeScreen
import com.byteflipper.mcbedrock.screens.NewsScreen
import com.byteflipper.mcbedrock.screens.SettingsScreen
import com.byteflipper.mcbedrock.screens.VideoScreen

sealed class NavigationItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : NavigationItem("home", Icons.Filled.Star, "Home")
    object Changelogs : NavigationItem("changelogs", Icons.Filled.Edit, "Changelogs")
    object Video : NavigationItem("video", Icons.Filled.MoreVert, "Video")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier,
    onTitleChange: (String) -> Unit
) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            onTitleChange("Домашний экран")
            HomeScreen(modifier = modifier, navController)
        }
        composable(NavigationItem.Changelogs.route) {
            onTitleChange("Чейнджлог")
            ChangelogesScreen(modifier = modifier)
        }
        composable(NavigationItem.Video.route) {
            onTitleChange("Видео")
            VideoScreen(modifier = modifier)
        }
        composable("settings") {
            onTitleChange("Настройки")
            SettingsScreen(modifier = modifier)
        }
        composable("news") {
            onTitleChange("Новости")
            NewsScreen(modifier = modifier, navController)
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier,
    onTitleChange: (String) -> Unit,
    scrollBehavior: TopAppBarDefaults // Добавляем параметр
) {
    NavHost(navController, startDestination = NavigationItem.Home.route, modifier = modifier) {
        composable(NavigationItem.Home.route) {
            onTitleChange("Домашний экран")
            HomeScreen(modifier = modifier, scrollBehavior = scrollBehavior) // Передаём scrollBehavior
        }
        composable(NavigationItem.About.route) {
            onTitleChange("О приложении")
            AboutScreen(modifier = modifier)
        }
        composable(NavigationItem.Settings.route) {
            onTitleChange("Настройки")
            SettingsScreen(modifier = modifier)
        }
    }
}*/


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Changelogs,
        NavigationItem.Video
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}