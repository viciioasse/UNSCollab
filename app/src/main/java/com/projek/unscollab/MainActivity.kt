package com.projek.unscollab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin : Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)

        val btnSignUp : Button = findViewById(R.id.btn_signUp)
        btnSignUp.setOnClickListener(this)

        val btnGoogle : Button = findViewById(R.id.btn_Google)
        btnGoogle.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_login -> {
                val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(loginIntent)
            }

            R.id.btn_signUp -> {
                val registerIntent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }

            R.id.btn_Google -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://accounts.google.com/")
                startActivity(intent)
            }
        }
    }
}