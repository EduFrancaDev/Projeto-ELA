package com.example.appandroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContatosSegurancaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos_seguranca)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerContatos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Simula lista de contatos cadastrados
        val contatos = listOf(
            Contato("Maria Silva", "(62) 91234-5678", "maria@email.com", R.drawable.foto1),
            Contato("João Santos", "(62) 99876-5432", "joao@email.com", R.drawable.foto2),
            Contato("Ana Lima", "(62) 92345-6789", "ana@email.com", R.drawable.foto3)
        )

        recyclerView.adapter = ContatoAdapter(contatos)
    }
}

// Dados do contato
data class Contato(val nome: String, val telefone: String, val email: String, val fotoResId: Int)

// Adaptador do RecyclerView
class ContatoAdapter(private val contatos: List<Contato>) : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    class ContatoViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nomeContato)
        val telefone: TextView = itemView.findViewById(R.id.telefoneContato)
        val email: TextView = itemView.findViewById(R.id.emailContato)
        val foto: ImageView = itemView.findViewById(R.id.fotoContato)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ContatoViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_contato, parent, false)
        return ContatoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = contatos[position]
        holder.nome.text = contato.nome
        holder.telefone.text = contato.telefone
        holder.email.text = contato.email
        holder.foto.setImageResource(contato.fotoResId)
    }

    override fun getItemCount() = contatos.size
}
