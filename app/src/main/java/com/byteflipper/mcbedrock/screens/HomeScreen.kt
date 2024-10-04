package com.byteflipper.mcbedrock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteflipper.mcbedrock.ChangelogEntry
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.R
import com.byteflipper.mcbedrock.fetchChangelogEntries
import com.byteflipper.mcbedrock.fetchNewsEntries
import com.byteflipper.mcbedrock.ui.component.HorizontalCarusel
import com.byteflipper.mcbedrock.ui.item.ChangelogItem
import com.byteflipper.mcbedrock.ui.item.HubEntry
import com.byteflipper.mcbedrock.ui.item.HubItem
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
        modifier = modifier
            .fillMaxSize()
    ) {


        HorizontalCarusel(
            items = newsEntries,
            itemContent = { entry -> NewsItemImage(entry) },
            maxItems = 5,
            navController = navController,
            navigationRoute = "news",
            carouselTitle = "Актуальные новости"
        )

        HorizontalDivider(
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 колонки
            modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp)
        ) {
            val hubEntries = listOf(
                HubEntry(title = "Bedrock", imageResId = R.drawable.minecraft_art, navigationRoute = "news"),
                HubEntry(title = "Java", imageResId = R.drawable.minecraft_art, navigationRoute = "changelog"),
                HubEntry(title = "Dungeons", imageResId = R.drawable.minecraft_art, navigationRoute = "news"),
                HubEntry(title = "Legends", imageResId = R.drawable.minecraft_art, navigationRoute = "changelog")
            )

            items(hubEntries) { entry ->
                HubItem(
                    hubEntry = entry,
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        navController = NavHostController(LocalContext.current)
    )
}