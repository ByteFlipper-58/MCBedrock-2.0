package com.byteflipper.mcbedrock.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VideoScreen(modifier: Modifier = Modifier) {
    Text(text = "Видео", modifier = modifier.padding(16.dp))
}
