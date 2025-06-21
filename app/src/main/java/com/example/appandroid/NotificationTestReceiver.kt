package com.example.appandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationTestReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        // Pega o token passado via ADB (ou usa um dummy)
        val token = intent.getStringExtra("token") ?: "DUMMY_TOKEN"
        // Dispara a notificação real de emergência
        NotificationHelper.triggerEmergencyNotification(ctx, token)
    }
}
