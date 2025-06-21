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

        // Cria canal de notificação
        NotificationHelper.createNotificationChannel(this)

        // Permissão Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        val cardChamado          = findViewById<LinearLayout>(R.id.cardChamadoAtivo)
        val textoSemChamado      = findViewById<TextView>(R.id.semChamadoText)
        val pergunta             = findViewById<TextView>(R.id.perguntaConfirmacao)
        val btnFinalizar         = findViewById<Button>(R.id.btnFinalizarChamado)
        val btnAbortar           = findViewById<Button>(R.id.btnAbortar)
        val btnContinuar         = findViewById<Button>(R.id.btnContinuar)
        val cardConfirmacaoFinal = findViewById<LinearLayout>(R.id.cardConfirmacaoFinal)
        val btnAcidental         = findViewById<Button>(R.id.btnAcidental)
        val btnIntencional       = findViewById<Button>(R.id.btnIntencional)
        val btnHistorico         = findViewById<Button>(R.id.btnHistorico)
        val btnPerfil            = findViewById<ImageButton>(R.id.btnPerfil)
        val btnContatosSeguranca = findViewById<Button>(R.id.btnContatosSeguranca)
        val btnTestarNotificacao = findViewById<Button>(R.id.btnTestarNotificacao)

        // Esconde botão finalizar até o momento certo
        btnFinalizar.visibility = View.GONE

        // Estado inicial
        atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado, cardConfirmacaoFinal)

        // Timer de confirmação
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
        }.also { it.start() }

        // Se o usuário abortar antes do fim do timer
        btnAbortar.setOnClickListener {
            chamadoAtivo = false
            atualizarStatusChamado(chamadoAtivo, cardChamado, textoSemChamado, cardConfirmacaoFinal)
            timer.cancel()
        }

        // Usuário confirma manualmente
        btnContinuar.setOnClickListener {
            pergunta.text = "Chamado confirmado."
            btnAbortar.visibility = View.GONE
            btnContinuar.visibility = View.GONE
            btnFinalizar.visibility = View.VISIBLE
            timer.cancel()
        }

        // Finaliza o chamado: mostra a confirmação final
        btnFinalizar.setOnClickListener {
            cardChamado.visibility = View.GONE
            cardConfirmacaoFinal.visibility = View.VISIBLE
        }

        // Depois de escolher “Acidental”
        btnAcidental.setOnClickListener {
            atualizarStatusChamado(false, cardChamado, textoSemChamado, cardConfirmacaoFinal)
            Toast.makeText(this, "Chamado descartado.", Toast.LENGTH_SHORT).show()
        }

        // Depois de escolher “Intencional”
        btnIntencional.setOnClickListener {
            atualizarStatusChamado(false, cardChamado, textoSemChamado, cardConfirmacaoFinal)
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
        btnTestarNotificacao.setOnClickListener {
            NotificationHelper.sendLiveCountdownNotification(this, token = "teste123")
        }
    }

    private fun atualizarStatusChamado(
        ativo: Boolean,
        cardChamado: LinearLayout,
        textoSemChamado: TextView,
        cardConfirmacaoFinal: LinearLayout
    ) {
        if (ativo) {
            cardChamado.visibility         = View.VISIBLE
            textoSemChamado.visibility     = View.GONE
            cardConfirmacaoFinal.visibility = View.GONE
        } else {
            cardChamado.visibility         = View.GONE
            textoSemChamado.visibility     = View.VISIBLE
            cardConfirmacaoFinal.visibility = View.GONE
        }
    }
}
