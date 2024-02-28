package com.example.custombroadcastreciverreciverapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun StringList(strings: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(strings.size) {
            val string = strings[it]
            val emoji = getFruitEmoji(string)
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(getRandomColor())
                    .padding(16.dp)
                    .clickable { /* Handle click action if needed */ }
            ) {
                Text(
                    text = emoji,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(32.dp)
                )
                Text(
                    text = string,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

private fun getRandomColor(): Color {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Cyan,
        Color.Yellow
    )
    return colors.random()
}

private fun getFruitEmoji(fruit: String): String {
    return when (fruit.toLowerCase(Locale.ROOT)) {
        "apple" -> "\uD83C\uDF4E"
        "orange" -> "\uD83C\uDF4A"
        "banana" -> "\uD83C\uDF4C"
        "grapes" -> "\uD83C\uDF47"
        "strawberry" -> "\uD83C\uDF53"
        "pineapple" -> "\uD83C\uDF4D"
        "watermelon" -> "\uD83C\uDF49"
        "mango" -> "\uD83C\uDF50"
        "kiwi" -> "\uD83E\uDD5D"
        "peach" -> "\uD83C\uDF51"
        else -> "\uD83C\uDF4E" // Default emoji
    }
}