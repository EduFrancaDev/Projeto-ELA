package com.example.appandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class ConfirmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val token = intent.getStringExtra("token")

        // ✅ Cancela a notificação interativa temporária (com cronômetro)
        NotificationManagerCompat.from(context).cancel(1001)

        // ✅ Mostra a notificação permanente disfarçada
        NotificationHelper.sendPermanentActiveNotification(context)

        // ✅ Feedback visual (pode remover se quiser)
        Toast.makeText(context, "Chamado confirmado com sucesso", Toast.LENGTH_SHORT).show()
    }
}
