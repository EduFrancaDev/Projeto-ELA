package com.example.appandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Oculta a ActionBar para manter o estilo limpo
        supportActionBar?.hide()

        // Configura o botão “Editar Perfil”
        val btnEditar = findViewById<Button>(R.id.btnEditarPerfil)
        btnEditar.setOnClickListener {
            // TODO: substituir por chamada real à tela de edição
            Toast.makeText(this, getString(R.string.editar_perfil), Toast.LENGTH_SHORT).show()
        }

        // Exemplo de carregamento de dados (caso queira preencher dinamicamente)
        val tvNome = findViewById<TextView>(R.id.tvNomePerfil)
        val tvEmail = findViewById<TextView>(R.id.tvEmailPerfil)
        // tvNome.text = "Nome real do usuário"
        // tvEmail.text = "email@dominio.com"
    }
}
