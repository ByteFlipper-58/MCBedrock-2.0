package com.byteflipper.mcbedrock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteflipper.mcbedrock.ChangelogEntry
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.fetchChangelogEntries
import com.byteflipper.mcbedrock.fetchNewsEntries
import com.byteflipper.mcbedrock.ui.component.HorizontalCarusel
import com.byteflipper.mcbedrock.ui.item.ChangelogItem
import com.byteflipper.mcbedrock.ui.item.NewsItemImage
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var newsEntries by remember { mutableStateOf<List<NewsEntry>>(emptyList()) }
    var changelogsEntries by remember { mutableStateOf<List<ChangelogEntry>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            newsEntries = fetchNewsEntries()
            changelogsEntries = fetchChangelogEntries("bedrockPatchNotes")
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


        HorizontalCarusel(
            items = newsEntries,
            itemContent = { entry -> NewsItemImage(entry) },
            maxItems = 5,
            navController = navController,
            navigationRoute = "news"
        )

        HorizontalDivider(
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
        )

        HorizontalCarusel(
            items = changelogsEntries,
            itemContent = { entry -> ChangelogItem(entry) },
            maxItems = 5,
            navController = navController,
            navigationRoute = "news"
        )

        HorizontalDivider(
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}