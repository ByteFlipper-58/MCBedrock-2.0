package com.byteflipper.mcbedrock.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.byteflipper.mcbedrock.Image
import com.byteflipper.mcbedrock.NavigationItem
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.R
import com.byteflipper.mcbedrock.ui.item.NewsItemImage
import com.byteflipper.mcbedrock.ui.theme.MCBedrockTheme

@Composable
fun HorizontalCarusel(entries: List<NewsEntry>, maxItems: Int, navController: NavHostController) {
    val displayEntries = entries.take(maxItems)
    val pagerState = rememberPagerState(pageCount = { displayEntries.size })

    MCBedrockTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.navigate("news")
                }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(.85f)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                text = "Horizontal Carusel",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
            )

            IconButton(
                modifier = Modifier.weight(.1f)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            NewsItemImage(entry = displayEntries[page])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHorizontalCarusel() {
    val sampleEntries = listOf(
        NewsEntry(
            title = "Sample News Title",
            category = "Category",
            date = "2024-09-27",
            text = "This is a sample text for the news entry. It may contain more than just a few words.",
            playPageImage = Image(
                title = "Play Page Image",
                drawableResId = R.drawable.minecraft_art
            ),
            newsPageImage = Image(
                title = "News Page Image",
                drawableResId = R.drawable.minecraft_art
            ),
            readMoreLink = "https://www.example.com",
            newsType = listOf("News", "Update"),
            id = "sample-id"
        ),
        NewsEntry(
            title = "Sample News Title 2",
            category = "Category",
            date = "2024-09-27",
            text = "This is a sample text for the news entry. It may contain more than just a few words.",
            playPageImage = Image(
                title = "Play Page Image",
                drawableResId = R.drawable.minecraft_art
            ),
            newsPageImage = Image(
                title = "News Page Image",
                drawableResId = R.drawable.minecraft_art
            ),
            readMoreLink = "https://www.example.com",
            newsType = listOf("News", "Update"),
            id = "sample-id-2"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalCarusel(entries = sampleEntries, maxItems = 1, navController = NavHostController(LocalContext.current))
    }
}
