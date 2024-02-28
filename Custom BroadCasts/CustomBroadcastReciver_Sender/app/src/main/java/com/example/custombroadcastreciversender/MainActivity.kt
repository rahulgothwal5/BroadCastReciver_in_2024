package com.example.custombroadcastreciversender

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.custombroadcastreciversender.ui.theme.CustomBroadcastReciver_SenderTheme

class MainActivity : ComponentActivity() {
    private val receiver = Receiver()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myFruits =
            intent.getStringArrayListExtra(MY_FRUITS) ?: listOf()

        registerReceiver()

        setContent {
            CustomBroadcastReciver_SenderTheme {
                // A surface container using the 'background' color from the theme
                val list by remember {
                    mutableStateOf(myFruits)
                }

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
                            Button(onClick = {
                                setupExternalIntent()
                                sendBroadcast(intent)
                            }) {
                                Text(text = "Send Broadcast data to other app")
                            }

                            Button(onClick = {
                                val intent = Intent(ACTION_CUSTOM_BROADCAST)
                                intent.putStringArrayListExtra(FRUIT_LIST, ArrayList(fruitList))
                                sendBroadcast(intent)
                            }) {
                                Text(text = "Send Broadcast to self")
                            }
                            if (myFruits.isEmpty().not()) {
                                LazyColumn {
                                    items(list.size) { index ->
                                        val fruit = list[index]
                                        val emoji = when (fruit) {
                                            "Apple" -> "\uD83C\uDF4E"
                                            "Orange" -> "\uD83C\uDF4A"
                                            "Banana" -> "\uD83C\uDF4C"
                                            "Grapes" -> "\uD83C\uDF47"
                                            "Strawberry" -> "\uD83C\uDF53"
                                            "Pineapple" -> "\uD83C\uDF4D"
                                            "Watermelon" -> "\uD83C\uDF49"
                                            "Mango" -> "\uD83C\uDF50"
                                            "Kiwi" -> "\uD83E\uDD5D"
                                            "Peach" -> "\uD83C\uDF51"
                                            else -> "\uD83C\uDF4E"
                                        }
                                        Text(text = "$emoji $fruit")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun registerReceiver() {
        val intentFilter =
            IntentFilter(ACTION_CUSTOM_BROADCAST)
        registerReceiver(receiver, intentFilter,RECEIVER_EXPORTED)
    }

    private fun setupExternalIntent() {
        val componentName = ComponentName(
            "com.example.custombroadcastreciverreciverapp",
            "com.example.custombroadcastreciverreciverapp.ExampleBroadCastReceiver"
        )
        intent.component = componentName
        intent.putStringArrayListExtra(FRUIT_LIST, ArrayList(fruitList))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}