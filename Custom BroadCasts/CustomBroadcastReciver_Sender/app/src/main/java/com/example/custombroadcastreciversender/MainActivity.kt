package com.example.custombroadcastreciversender

import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.custombroadcastreciversender.ui.theme.CustomBroadcastReciver_SenderTheme

class MainActivity : ComponentActivity() {
    val fruitList = listOf(
        "Apple",
        "Orange",
        "Banana",
        "Grapes",
        "Strawberry",
        "Pineapple",
        "Watermelon",
        "Mango",
        "Kiwi",
        "Peach"
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        val componentName = ComponentName(
            "com.example.custombroadcastreciverreciverapp",
            "com.example.custombroadcastreciverreciverapp.ExampleBroadCastReceiver"
        )
        intent.component = componentName
        intent.putStringArrayListExtra("fruit_list", ArrayList(fruitList))

        super.onCreate(savedInstanceState)
        setContent {
            CustomBroadcastReciver_SenderTheme {
                // A surface container using the 'background' color from the theme

                Scaffold(
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(12.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sender App",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = { sendBroadcast(intent) }) {
                                Text(text = "Send Broad Cast Data")
                            }
                        }
                    }
                }
            }
        }
    }
}