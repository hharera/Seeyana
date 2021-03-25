package com.example.n_one.Activites

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.n_one.R


class SignUpWelcomeActivity : AppCompatActivity() {
    private lateinit var btnEnter: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_signup_welcome)
        btnEnter = findViewById(R.id.btnEnter)

        btnEnter.setOnClickListener {
            startActivity(
                Intent(
                    this@SignUpWelcomeActivity,
                    SecondProgressActivity::class.java
                )
            )
        }
    }
}