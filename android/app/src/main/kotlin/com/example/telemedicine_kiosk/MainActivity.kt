package com.example.telemedicine_kiosk

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "kiosk"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                try {
                    when (call.method) {
                        "startKiosk" -> {
                            startLockTask()
                            result.success(true)
                        }
                        "stopKiosk" -> {
                            stopLockTask()
                            result.success(true)
                        }
                        else -> result.notImplemented()
                    }
                } catch (e: Exception) {
                    result.error("KIOSK_ERROR", e.message, null)
                }
            }
    }

    @Deprecated("We deliberately block back in kiosk")
    override fun onBackPressed() {
        // Disable back button while in kiosk mode
    }
}
