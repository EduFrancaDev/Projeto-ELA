package com.example.appandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {

    const val CHANNEL_ID = "emergency_channel"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Emergência"
            val description = "Notificações urgentes e discretas"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                enableVibration(true)
                enableLights(false)
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendLiveCountdownNotification(context: Context, token: String) {
        val notificationId = 1001

        val confirmIntent = Intent(context, ConfirmReceiver::class.java).apply {
            action = "CONFIRM_ACTION"
            putExtra("token", token)
        }
        val confirmPendingIntent = PendingIntent.getBroadcast(
            context, 0, confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val cancelIntent = Intent(context, CancelReceiver::class.java).apply {
            action = "CANCEL_ACTION"
            putExtra("token", token)
        }
        val cancelPendingIntent = PendingIntent.getBroadcast(
            context, 1, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val finishTime = System.currentTimeMillis() + 60000 // 60 segundos

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_perfil)
            .setContentTitle("Chamado ativo")
            .setContentText("Ela está aguardando...")
            .setWhen(finishTime)
            .setUsesChronometer(true)
            .setChronometerCountDown(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_perfil, "Cancelar", cancelPendingIntent)
            .addAction(R.drawable.ic_perfil, "Confirmar", confirmPendingIntent)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }

        // Dispara confirmação automática após 60 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(context, AutoConfirmReceiver::class.java).apply {
                action = "AUTO_CONFIRM"
                putExtra("token", token)
            }
            context.sendBroadcast(intent)
        }, 60000)
    }

    fun sendPermanentActiveNotification(context: Context) {
        val notificationId = 1002

        val openIntent = Intent(context, TelaPrincipal::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val openPendingIntent = PendingIntent.getActivity(
            context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_perfil)
            .setContentTitle("E.L.A.")
            .setContentText("Ela está ativa")
            .setContentIntent(openPendingIntent)
            .setOngoing(true) // impede swipe
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }
}
