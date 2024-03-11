package com.limjihoon.subwaytoilet.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.limjihoon.subwaytoilet.R


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },3000)


    }
}