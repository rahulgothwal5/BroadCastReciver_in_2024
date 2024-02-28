package com.example.custombroadcastreciversender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_CUSTOM_BROADCAST) {
            // Extract the extra data from the Intent
            intent.getStringArrayListExtra(FRUIT_LIST)?.let {
                val mIntent = Intent(context, MainActivity::class.java).apply {
                    putStringArrayListExtra(MY_FRUITS, ArrayList(it))
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context?.startActivity(mIntent)
            }
        }
    }
}