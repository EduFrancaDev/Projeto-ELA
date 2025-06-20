package com.example.appandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appandroid.NotificationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Cria o canal de notificação ao iniciar o app
        NotificationHelper.createNotificationChannel(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoCadastrar = findViewById<Button>(R.id.botaoCadastrar)
        botaoCadastrar.setOnClickListener {
            // Aqui você pode validar os campos, se quiser

            // Abre a tela principal
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)

            // Fecha a tela de cadastro (opcional)
            finish()
        }
    }
}
