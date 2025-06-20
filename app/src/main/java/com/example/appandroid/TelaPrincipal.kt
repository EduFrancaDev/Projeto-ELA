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
        val btnPerfil = findViewById<ImageButton>(R.id.btnPerfil)
        val cardConfirmacaoFinal = findViewById<LinearLayout>(R.id.cardConfirmacaoFinal)
        val btnAcidental = findViewById<Button>(R.id.btnAcidental)
        val btnIntencional = findViewById<Button>(R.id.btnIntencional)
        val btnContatosSeguranca = findViewById<Button>(R.id.btnContatosSeguranca)

        // Oculta o botão "Finalizar chamado" no início
        btnFinalizar.visibility = View.GONE

        // Atualiza a interface com base no estado inicial
        atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado)

        // Timer de 60 segundos para confirmação
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
            // Oculta chamado e exibe o card de confirmação final
            cardChamado.visibility = View.GONE
            cardConfirmacaoFinal.visibility = View.VISIBLE
        }

        btnAcidental.setOnClickListener {
            // Apenas fecha o card
            cardConfirmacaoFinal.visibility = View.GONE
            textoSemChamado.visibility = View.VISIBLE
            Toast.makeText(this, "Chamado descartado.", Toast.LENGTH_SHORT).show()
        }

        btnIntencional.setOnClickListener {
            // Aqui salvaríamos no histórico (futuro)
            cardConfirmacaoFinal.visibility = View.GONE
            textoSemChamado.visibility = View.VISIBLE
            Toast.makeText(this, "Chamado registrado no histórico.", Toast.LENGTH_SHORT).show()
        }

        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java)
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        btnContatosSeguranca.setOnClickListener {
            val intent = Intent(this, ContatosSegurancaActivity::class.java)
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
