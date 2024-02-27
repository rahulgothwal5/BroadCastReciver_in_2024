package com.example.hiltbroadcastreciever

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hiltbroadcastreciever.broadcastreciever.HiltBroadCastReceiver
import com.example.hiltbroadcastreciever.data.model.Product
import com.example.hiltbroadcastreciever.ui.ProductItem
import com.example.hiltbroadcastreciever.ui.TopBar
import com.example.hiltbroadcastreciever.ui.theme.HiltBroadcastRecieverTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var hiltBroadCastReceiver: HiltBroadCastReceiver

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val products =
            intent.getParcelableArrayListExtra("product_data", Product::class.java) ?: listOf()

        setContent {
            HiltBroadcastRecieverTheme {

                val list by remember {
                    mutableStateOf(products)
                }

                Scaffold(
                    topBar = {
                        TopBar()
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (list.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Please toggle AirPlane mode",
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        } else {
                            LazyColumn {
                                items(products.size) { index ->
                                    val product = products[index]
                                    ProductItem(product = product)
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(hiltBroadCastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        unregisterReceiver(hiltBroadCastReceiver)
    }


}
