package com.limjihoon.subwaytoilet.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.limjihoon.subwaytoilet.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
        binding.tvLogin.setOnClickListener { startActivity(Intent(this, LogonActivity::class.java)) }
        binding.tvSignup.setOnClickListener { startActivity(Intent(this, SginupActivity::class.java)) }
        binding.tvLogin.setOnClickListener { startActivity(Intent(this, LogonActivity::class.java)) }


    }


}