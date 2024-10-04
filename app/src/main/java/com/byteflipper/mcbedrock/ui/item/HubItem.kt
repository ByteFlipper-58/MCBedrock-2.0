package com.byteflipper.mcbedrock.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.byteflipper.mcbedrock.R

@Composable
fun HubItem(
    hubEntry: HubEntry,
    navController: NavHostController?
) {
    ElevatedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                navController?.navigate(hubEntry.navigationRoute)
            },
        shape = RoundedCornerShape(21.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = hubEntry.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(21.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = hubEntry.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class HubEntry(
    val title: String,
    val imageResId: Int,
    val navigationRoute: String
)

@Preview
@Composable
fun HubItemPreview() {
    HubItem(
        hubEntry = HubEntry(
            title = "Hub Item Title",
            imageResId = R.drawable.minecraft_art,
            navigationRoute = "some_route"
        ),
        navController = null
    )
}