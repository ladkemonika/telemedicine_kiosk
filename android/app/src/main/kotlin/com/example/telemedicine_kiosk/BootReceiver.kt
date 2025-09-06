package com.example.telemedicine_kiosk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

// e.g., com.example.telemedicine_kiosk

class BootReceiver : BroadcastReceiver() {
    public override fun onReceive(context: Context, intent: Intent) {
        val action: String? = intent.getAction()
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) ||
            Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)
        ) {
            try {
                val launch: Intent = Intent(context, MainActivity::class.java)
                launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(launch)
            } catch (e: Exception) {
                Log.e("BootReceiver", "Failed to start activity on boot: " + e.message)
            }
        }
    }
}