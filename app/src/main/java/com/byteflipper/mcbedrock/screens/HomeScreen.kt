package com.byteflipper.mcbedrock.screens

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.fetchNewsEntries
import com.byteflipper.mcbedrock.ui.item.NewsItemDefault
import kotlinx.coroutines.launch

@Composable
@Preview(showBackground = true)
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        var newsEntries by remember { mutableStateOf<List<NewsEntry>>(emptyList()) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch {
                newsEntries = fetchNewsEntries()
            }
        }

        LazyColumn {
            items(newsEntries) { entry ->
                NewsItemDefault(entry = entry)
            }
        }
    }
}

fun openLinkInCustomTab(url: String, context: Context) {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}