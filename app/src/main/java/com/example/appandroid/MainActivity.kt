package com.example.appandroid

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val REQUEST_CAMERA_PERMISSION = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Cria canal de notificação e ajusta padding para barras de sistema
        NotificationHelper.createNotificationChannel(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa pickers de imagem
        setupImagePickers()

        // Botão Cadastrar: sempre navega para TelaPrincipal
        findViewById<Button>(R.id.botaoCadastrar).setOnClickListener {
            startActivity(Intent(this, TelaPrincipal::class.java))
            finish()
        }

        // Texto “Já tem uma conta? Faça Login”
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val fullText = "Já tem uma conta? Faça Login"
        val linkText = "Faça Login"
        val spannable = SpannableString(fullText)
        val start = fullText.indexOf(linkText)
        val end = start + linkText.length

        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvLogin.text = spannable
        tvLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupImagePickers() {
        imageView = findViewById(R.id.fotoPerfil)
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            (result.data?.extras?.get("data") as? Bitmap)?.let { imageView.setImageBitmap(it) }
        }
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let { imageView.setImageURI(it) }
        }
        imageView.setOnClickListener {
            val options = arrayOf("Abrir Câmera", "Escolher da Galeria")
            AlertDialog.Builder(this)
                .setTitle("Selecionar Foto")
                .setItems(options) { _, which ->
                    if (which == 0) checkCameraPermission() else openGallery()
                }
                .show()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Permissão da câmera negada", Toast.LENGTH_SHORT).show()
        }
    }
}
