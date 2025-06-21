package com.example.appandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HistoricoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout reestilizado
        setContentView(R.layout.activity_historico)

        // Oculta a barra de ação para manter o visual limpo
        supportActionBar?.hide()

        // Configura o botão “Voltar” para fechar esta Activity
        val btnVoltar = findViewById<Button>(R.id.btnVoltarHistorico)
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
