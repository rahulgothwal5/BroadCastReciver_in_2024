package com.example.hiltbroadcastreciever.broadcastreciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import com.example.hiltbroadcastreciever.MainActivity
import com.example.hiltbroadcastreciever.data.repo.ProductRepository
import com.example.hiltbroadcastreciever.data.Result
import com.example.hiltbroadcastreciever.data.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HiltBroadCastReceiver @Inject constructor(
    private val repo: ProductRepository
) : BroadcastReceiver() {
    private val TAG = "BROADCAST_RECEIVER"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "BroadcastReceiver triggered")
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: false
            if (isAirplaneModeEnabled) {
                // Airplane mode is enabled
                Log.d(TAG, "Airplane mode is enabled")
                sendIntent(context, listOf())
            } else {
                // Airplane mode is disabled
                Log.d(TAG, "Airplane mode is disabled")
                fetchProducts(context)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun fetchProducts(context: Context?) {
        GlobalScope.launch {
            Log.d(TAG, "Queuing request")
            delay(5000)
            repo.getProducts().collect { result ->
                when (result) {

                    is Result.Success -> {
                        Log.d(TAG, result.data?.first().toString())

                        val list = result.data

                        sendIntent(context, list)
                    }

                    is Result.Error -> {
                        Log.d(TAG, result.exception.message.toString())
                    }
                }
            }
        }
    }

    private fun sendIntent(
        context: Context?,
        list: List<Product>?
    ) {
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            putParcelableArrayListExtra("product_data", ArrayList<Product>(list))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // Send the intent
        context?.startActivity(activityIntent)
    }
}



