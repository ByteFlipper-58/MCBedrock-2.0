package com.byteflipper.mcbedrock

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteflipper.mcbedrock.screens.SplashScreen
import com.byteflipper.mcbedrock.ui.theme.MCBedrockTheme
import com.byteflipper.mcbedrock.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Создайте анимацию растворения.
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.ALPHA,
                1f, 0f
            )

            // Создайте анимацию уменьшения по оси X.
            val scaleX = ObjectAnimator.ofFloat(
                splashScreenView.iconView, // Анимируем иконку
                View.SCALE_X,
                1f, .75f // Уменьшаем иконку до 50% от исходного размера
            )

            // Создайте анимацию уменьшения по оси Y.
            val scaleY = ObjectAnimator.ofFloat(
                splashScreenView.iconView, // Анимируем иконку
                View.SCALE_Y,
                1f, .75f // Уменьшаем иконку до 50% от исходного размера
            )

            fadeOut.interpolator = DecelerateInterpolator()
            scaleX.interpolator = DecelerateInterpolator()
            scaleY.interpolator = DecelerateInterpolator()

            fadeOut.duration = 200L
            scaleX.duration = 200L
            scaleY.duration = 200L

            // Группируем анимации в AnimatorSet, чтобы запустить их одновременно.
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(fadeOut, scaleX, scaleY)

            // Удаляем SplashScreenView в конце анимации.
            animatorSet.doOnEnd { splashScreenView.remove() }

            // Запускаем анимацию.
            animatorSet.start()
        }


        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (viewModel.isReady) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.

                        false
                    }
                }
            }
        )

        setContent {
            //SplashScreen()
            MainActivityContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent() {
    MCBedrockTheme {
        val navController = rememberNavController()
        var toolbarTitle by remember { mutableStateOf("Главный экран") }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surface,

            topBar = {
                TopAppBar(
                    title = { Text(toolbarTitle) },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        if (currentRoute != "settings") {
                            IconButton(onClick = {
                                navController.navigate("settings")
                            }) {
                                Icon(Icons.Default.Settings, contentDescription = "Настройки")
                            }
                        }
                    }
                )
            },
            bottomBar = {
                if (currentRoute != "settings") {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) {
            innerPadding ->
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            NavigationHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                onTitleChange = { newTitle -> toolbarTitle = newTitle }
            )
        }
    }
}


@PreviewDynamicColors
@Composable
@Preview(showBackground = true)
fun MainActivityPreview() {
    MainActivityContent()
}