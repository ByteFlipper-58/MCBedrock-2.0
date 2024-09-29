package com.byteflipper.mcbedrock.ui.item

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.byteflipper.mcbedrock.Image
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.R
import com.byteflipper.mcbedrock.ui.theme.MCBedrockTheme

@Composable
fun NewsItemDefault(entry: NewsEntry) {
    val context = LocalContext.current
    MCBedrockTheme {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(21.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                NewsImage(entry)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = entry.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = entry.newsType.joinToString(", "),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = entry.text,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                ActionRow(entry, context)
            }
        }
    }
}

@Composable
fun NewsItemImage(entry: NewsEntry) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        if (entry.newsPageImage.url != null) {
            Image(
                painter = rememberAsyncImagePainter("https://launchercontent.mojang.com${entry.newsPageImage.url}"),
                contentDescription = entry.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(21.dp))
            )
        } else if (entry.newsPageImage.drawableResId != null) {
            Image(
                painter = painterResource(id = entry.newsPageImage.drawableResId),
                contentDescription = entry.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(21.dp))
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.title,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            //Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = entry.text,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            //Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = entry.newsType.joinToString(", "),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun NewsImage(entry: NewsEntry) {
    if (entry.newsPageImage.url != null) {
        Image(
            painter = rememberAsyncImagePainter("https://launchercontent.mojang.com${entry.newsPageImage.url}"),
            contentDescription = entry.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    } else if (entry.newsPageImage.drawableResId != null) {
        Image(
            painter = painterResource(id = entry.newsPageImage.drawableResId),
            contentDescription = entry.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(172.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun ActionRow(entry: NewsEntry, context: android.content.Context) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        /*Text(
            modifier = Modifier.weight(1f),
            text = entry.date.replace("-", ".")
        )*/
        FilledTonalButton(
            modifier = Modifier,
            onClick = { openLinkInCustomTab(entry.readMoreLink, context) }
        ) {
            Text(text = "Read More")
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewNewsItems() {
    val sampleEntry = NewsEntry(
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
    )

    Column {
        NewsItemImage(entry = sampleEntry)
        NewsItemDefault(entry = sampleEntry)
    }
}

fun openLinkInCustomTab(url: String, context: Context) {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}