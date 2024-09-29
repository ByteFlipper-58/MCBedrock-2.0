package com.byteflipper.mcbedrock.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.byteflipper.mcbedrock.ChangelogEntry
import com.byteflipper.mcbedrock.ChangelogImage
import com.byteflipper.mcbedrock.Image
import com.byteflipper.mcbedrock.NewsEntry
import com.byteflipper.mcbedrock.R

@Composable
fun ChangelogItem(entry: ChangelogEntry) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        if (entry.image.url != null) {
            Image(
                painter = rememberAsyncImagePainter("https://launchercontent.mojang.com${entry.image.url}"),
                contentDescription = entry.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(21.dp))
            )
        } else if (entry.image.drawableResId != null) {
            Image(
                painter = painterResource(id = entry.image.drawableResId),
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
                text = entry.shortText,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            //Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = entry.version,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun ChangelogImage(entry: NewsEntry) {
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
fun ChangelogActionRow(entry: ChangelogEntry, context: android.content.Context) {
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
            onClick = { openLinkInCustomTab(entry.contentPath, context) }
        ) {
            Text(text = "Read More")
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewChangelogItems() {
    val sampleEntry = ChangelogEntry(
        title = "Sample News Title",
        version = "1.21.30",
        patchNoteType = "Update",
        date = "2024-09-27",
        image = ChangelogImage(
            title = "Play Page Image",
            drawableResId = R.drawable.minecraft_art
        ),
        contentPath = "https://www.example.com",
        id = "sample-id",
        shortText = "This is a sample text for the news entry. It may contain more than just a few words."
    )

    Column {
        ChangelogItem(entry = sampleEntry)
    }
}