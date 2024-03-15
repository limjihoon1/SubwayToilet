package com.limjihoon.subwaytoilet.activites

import android.content.pm.PackageManager
import android.location.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.gson.internal.reflect.ReflectionHelper
import com.limjihoon.subwaytoilet.BuildConfig

import com.limjihoon.subwaytoilet.R
import com.limjihoon.subwaytoilet.data.Accc

import com.limjihoon.subwaytoilet.data.Body
import com.limjihoon.subwaytoilet.data.StationDataSearch

import com.limjihoon.subwaytoilet.databinding.ActivityMainBinding
import com.limjihoon.subwaytoilet.fragment.LastFragment
import com.limjihoon.subwaytoilet.fragment.MyMapFragment
import com.limjihoon.subwaytoilet.fragment.ReviewFragment
import com.limjihoon.subwaytoilet.fragment.ReviewSearchFragment
import com.limjihoon.subwaytoilet.network.RetrofitService
import com.limjihoon.subwaytoilet.network.RtrofitHelper
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var myLocation: Location? = null
    val locationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            this
        )
    }
    var q = "S1"
    var w = "3"
    var e = "322"
    var lastData: Accc? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, MyMapFragment())
            .commit()

        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_first -> supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, MyMapFragment()).commit()

                R.id.menu_secund -> supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, ReviewFragment()).commit()

                R.id.menu_third -> supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, ReviewSearchFragment()).commit()

                R.id.menu_forth -> supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, LastFragment()).commit()


            }
            true
        }
        val permissionstate =
            checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == true) {
                    startLast()
                } else {
                    AlertDialog.Builder(this).setMessage("위치정보 허용 해야함").create().show()
                    finish()
                }

            }
        if (permissionstate == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            Toast.makeText(this, "위치정보 허용", Toast.LENGTH_SHORT).show()
        } else {
            startLast()
        }
        binding.btn.setOnClickListener {
            placeSearch()

        }
    }


    private fun startLast() {
        val request: LocationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        if (ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        )
            return
        locationProviderClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            myLocation = p0.lastLocation
            locationProviderClient.removeLocationUpdates(this)
            Serch()
        }

    }


    var quit: MutableList<StationDataSearch> = mutableListOf()

    private fun Serch() {
        val inputStream = assets.open("station.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val builder = StringBuilder()
        while (true) {
            var line = bufferedReader.readLine() ?: break
            builder.append(line)
        }
        val jsonArray: JSONArray = JSONArray(builder.toString())
        for (i in 0 until jsonArray.length()) {
            val jo = jsonArray.getJSONObject(i)

            var qq = jo.getString("RAIL_OPR_ISTT_CD")
            var ww = jo.getString("LN_CD")
            var ee = jo.getString("STIN_CD")
            var name = jo.getString("STIN_NM")

            val show = StationDataSearch(qq, ww, ee, name)
            quit.add(show)

        }
        loop@ for (i in 0 until quit.size) {
            if (quit.get(i).STIN_NM == binding.et.text.toString()) {
                val retrofit = RtrofitHelper.getRetrofitInstance("https://openapi.kric.go.kr")
                val retrofitService = retrofit.create(RetrofitService::class.java)
                val call = retrofitService.getLastApi(
                    BuildConfig.api_key, "json",
                    quit.get(i).RAIL_OPR_ISTT_CD, quit.get(i).LN_CD, quit.get(i).STIN_CD
                )
//
                call.enqueue(object : Callback<Accc> {
                    override fun onResponse(call: Call<Accc>, response: Response<Accc>) {
                        lastData = response.body()
                        binding.bnv.selectedItemId = R.id.menu_forth
                    }

                    override fun onFailure(call: Call<Accc>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "ㅗㅗㅗㅗㅗㅗㅗㅗㅗ", Toast.LENGTH_SHORT).show()
                    }

                })
                break
            } else {
                continue@loop
            }
        }
    }

    private fun placeSearch() {
        startLast()
    }
}

