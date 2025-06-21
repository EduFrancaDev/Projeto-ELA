package com.example.appandroid

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Botão Entrar: sempre vai para TelaPrincipal
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            startActivity(Intent(this, TelaPrincipal::class.java))
            finish()
        }

        // Texto “Não tem uma conta? Cadastre-se”
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val fullText = "Não tem uma conta? Cadastre-se"
        val spannable = SpannableString(fullText)
        val linkText = "Cadastre-se"
        val start = fullText.indexOf(linkText)
        val end = start + linkText.length

        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Abre a tela de cadastro
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }, start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvRegister.text = spannable
        tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }
}
