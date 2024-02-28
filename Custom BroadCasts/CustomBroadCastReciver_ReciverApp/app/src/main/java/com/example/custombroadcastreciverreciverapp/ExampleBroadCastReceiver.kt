package com.example.custombroadcastreciverreciverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ExampleBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("CUSTOM_BROADCAST", "Receiver App BroadCast Received")
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            putStringArrayListExtra(
                "fruit_list",
                ArrayList(intent?.getStringArrayListExtra("fruit_list"))
            )
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // Send the intent
        context?.startActivity(activityIntent)
    }
}