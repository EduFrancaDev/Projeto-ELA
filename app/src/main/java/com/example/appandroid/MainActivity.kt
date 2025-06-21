package com.example.appandroid

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appandroid.NotificationHelper

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private val REQUEST_CAMERA_PERMISSION = 1001

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
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        imageView = findViewById(R.id.fotoPerfil)

        // Registradores de resultado para câmera e galeria
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            imageBitmap?.let { imageView.setImageBitmap(it) }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val uri: Uri? = result.data?.data
            uri?.let { imageView.setImageURI(it) }
        }

        // Clique na imagem da câmera
        imageView.setOnClickListener {
            val options = arrayOf("Abrir Câmera", "Escolher da Galeria")
            AlertDialog.Builder(this)
                .setTitle("Selecionar Foto")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> verificarPermissaoCamera()
                        1 -> abrirGaleria()
                    }
                }
                .show()
        }
    }

    private fun verificarPermissaoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            abrirCamera()
        }
    }

    private fun abrirCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamera()
            } else {
                Toast.makeText(this, "Permissão da câmera negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
