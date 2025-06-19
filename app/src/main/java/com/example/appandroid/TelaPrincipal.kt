package com.example.appandroid

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TelaPrincipal : AppCompatActivity() {

    private var chamadoAtivo = true
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal)

        val cardChamado = findViewById<LinearLayout>(R.id.cardChamadoAtivo)
        val textoSemChamado = findViewById<TextView>(R.id.semChamadoText)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizarChamado)
        val btnAbortar = findViewById<Button>(R.id.btnAbortar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val pergunta = findViewById<TextView>(R.id.perguntaConfirmacao)
        val btnHistorico = findViewById<Button>(R.id.btnHistorico)

        // Oculta o botão "Finalizar chamado" no início
        btnFinalizar.visibility = View.GONE

        // Atualiza a interface com base no estado inicial
        atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado)

        // Timer de 60 segundos para confirmação
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pergunta.text = "Foi um chamado acidental ou intencional? (${millisUntilFinished / 1000}s)"
            }

            override fun onFinish() {
                pergunta.text = "Chamado confirmado automaticamente."
                btnAbortar.visibility = View.GONE
                btnContinuar.visibility = View.GONE
                btnFinalizar.visibility = View.VISIBLE
                // Aqui você pode acionar backend ou notificações, se quiser
            }
        }
        timer.start()

        btnFinalizar.setOnClickListener {
            chamadoAtivo = false
            atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado)
            timer.cancel()
        }

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

        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java)
            startActivity(intent)
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
