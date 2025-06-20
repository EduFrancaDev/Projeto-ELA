package com.example.appandroid // ajuste conforme seu pacote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class AutoConfirmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val token = intent.getStringExtra("token")

        // Aqui você pode chamar o endpoint GET /nfc/auto/<token>

        Toast.makeText(context, "Chamado confirmado automaticamente", Toast.LENGTH_SHORT).show()

        // Opcional: trocar a notificação por uma permanente
        // Ou apenas atualizar o status interno

        // Cancela a notificação interativa (caso ainda esteja na tela)
        NotificationManagerCompat.from(context).cancel(1001)
    }
}
