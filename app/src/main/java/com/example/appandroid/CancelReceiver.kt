package com.example.appandroid // ajuste para o seu pacote real

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class CancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val token = intent.getStringExtra("token")

        // Aqui você pode chamar o endpoint POST /abort/<token>

        Toast.makeText(context, "Chamado cancelado", Toast.LENGTH_SHORT).show()

        // Cancela a notificação ativa
        NotificationManagerCompat.from(context).cancel(1001)
    }
}
