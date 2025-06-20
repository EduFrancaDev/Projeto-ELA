package com.example.appandroid

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.appandroid.NotificationHelper

class TelaPrincipal : AppCompatActivity() {

    private var chamadoAtivo = true
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal)

        // ✅ Parte 1: Cria canal de notificação
        NotificationHelper.createNotificationChannel(this)

        // ✅ Parte 2: Pede permissão no Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        val cardChamado = findViewById<LinearLayout>(R.id.cardChamadoAtivo)
        val textoSemChamado = findViewById<TextView>(R.id.semChamadoText)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizarChamado)
        val btnAbortar = findViewById<Button>(R.id.btnAbortar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val pergunta = findViewById<TextView>(R.id.perguntaConfirmacao)
        val btnHistorico = findViewById<Button>(R.id.btnHistorico)
        val btnPerfil = findViewById<ImageButton>(R.id.btnPerfil)
        val cardConfirmacaoFinal = findViewById<LinearLayout>(R.id.cardConfirmacaoFinal)
        val btnAcidental = findViewById<Button>(R.id.btnAcidental)
        val btnIntencional = findViewById<Button>(R.id.btnIntencional)
        val btnContatosSeguranca = findViewById<Button>(R.id.btnContatosSeguranca)
        val btnTestarNotificacao = findViewById<Button>(R.id.btnTestarNotificacao)

        btnFinalizar.visibility = View.GONE

        atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado)

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pergunta.text = "Você chamou a E.L.A. Posso confirmar ou foi um acidente? (${millisUntilFinished / 1000}s)"
            }

            override fun onFinish() {
                pergunta.text = "Chamado confirmado automaticamente."
                btnAbortar.visibility = View.GONE
                btnContinuar.visibility = View.GONE
                btnFinalizar.visibility = View.VISIBLE
            }
        }
        timer.start()

        btnAbortar.setOnClickListener {
            chamadoAtivo = false
            atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado)
            timer.cancel()
        }

        btnContinuar.setOnClickListener {
            pergunta.text = "Chamado confirmado."
            btnAbortar.visibility = View.GONE
            btnContinuar.visibility = View.GONE
            btnFinalizar.visibility = View.VISIBLE
            timer.cancel()
        }

        btnFinalizar.setOnClickListener {
            cardChamado.visibility = View.GONE
            cardConfirmacaoFinal.visibility = View.VISIBLE
        }

        btnAcidental.setOnClickListener {
            cardConfirmacaoFinal.visibility = View.GONE
            textoSemChamado.visibility = View.VISIBLE
            Toast.makeText(this, "Chamado descartado.", Toast.LENGTH_SHORT).show()
        }

        btnIntencional.setOnClickListener {
            cardConfirmacaoFinal.visibility = View.GONE
            textoSemChamado.visibility = View.VISIBLE
            Toast.makeText(this, "Chamado registrado no histórico.", Toast.LENGTH_SHORT).show()
        }

        btnHistorico.setOnClickListener {
            startActivity(Intent(this, HistoricoActivity::class.java))
        }

        btnPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        btnContatosSeguranca.setOnClickListener {
            startActivity(Intent(this, ContatosSegurancaActivity::class.java))
        }

        // 🔔 Teste da notificação com cronômetro
        btnTestarNotificacao.setOnClickListener {
            NotificationHelper.sendLiveCountdownNotification(this, token = "teste123")
        }
    }

    private fun atualizarStatusChamado(
        ativo: Boolean,
        card: LinearLayout,
        textoSem: TextView
    ) {
        if (ativo) {
            card.visibility = View.VISIBLE
            textoSem.visibility = View.GONE
        } else {
            card.visibility = View.GONE
            textoSem.visibility = View.VISIBLE
        }
    }
}
