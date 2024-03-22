package com.limjihoon.subwaytoilet.activites

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.limjihoon.subwaytoilet.R
import com.limjihoon.subwaytoilet.data.DocumentOfPlace
import com.limjihoon.subwaytoilet.databinding.ActivityDitailBinding

class DitailActivity : AppCompatActivity() {

    lateinit var binding :ActivityDitailBinding
    lateinit var liteDB:SQLiteDatabase
    lateinit var place: DocumentOfPlace
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDitailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lastPlace :String? =intent.getStringExtra("place")
        lastPlace?.also {
            place =Gson().fromJson(it,DocumentOfPlace::class.java)

            binding.webview.webViewClient = WebViewClient()
            binding.webview.webChromeClient = WebChromeClient()
            binding.webview.settings.javaScriptEnabled =true

            binding.webview.loadUrl("https://www.naver.com")

        }
        liteDB=openOrCreateDatabase("place", MODE_PRIVATE,null)

        liteDB.execSQL("CREATE TABLE IF NOT EXISTS favor(id TEXT PRIMARY KEY,place_name TEXT,category_name TEXT,phone TEXT,addreess_name TEXT,load_adrress_name TEXT,x TEXT,y TEXT,place_url TEXT,distance TEXT)")

    }
}