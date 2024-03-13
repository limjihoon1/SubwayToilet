package com.limjihoon.subwaytoilet.activites

import android.content.pm.PackageManager
import android.location.Location
import android.net.DnsResolver.Callback

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

import com.limjihoon.subwaytoilet.R
import com.limjihoon.subwaytoilet.data.Alldatapl

import com.limjihoon.subwaytoilet.databinding.ActivityMainBinding
import com.limjihoon.subwaytoilet.fragment.LastFragment
import com.limjihoon.subwaytoilet.fragment.MyMapFragment
import com.limjihoon.subwaytoilet.fragment.ReviewFragment
import com.limjihoon.subwaytoilet.fragment.ReviewSearchFragment
import com.limjihoon.subwaytoilet.network.RetrofitHelper
import com.limjihoon.subwaytoilet.network.RetrofitService
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var myLocation:Location?=null
    val locationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    var q = "S1"
    var w = "3"
    var e = "322"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,MyMapFragment()).commit()

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_first ->supportFragmentManager.beginTransaction().replace(R.id.frame_layout,MyMapFragment()).commit()
                R.id.menu_secund ->supportFragmentManager.beginTransaction().replace(R.id.frame_layout,ReviewFragment()).commit()
                R.id.menu_third ->supportFragmentManager.beginTransaction().replace(R.id.frame_layout,ReviewSearchFragment()).commit()
                R.id.menu_forth -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout,LastFragment()).commit()


            }
            true
        }
        val permissionstate =checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionLauncher:ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it ==true){
                startLast()
            }else{
                AlertDialog.Builder(this).setMessage("위치정보 허용 해야함").create().show()
                finish()
            }

        }
        if (permissionstate == PackageManager.PERMISSION_DENIED){
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            Toast.makeText(this, "위치정보 허용", Toast.LENGTH_SHORT).show()
        }else{
            startLast()
        }
        binding.btn.setOnClickListener {
            placeSearch()

        }
    }


    private fun startLast(){
        val request:LocationRequest=LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

        if (ActivityCompat.checkSelfPermission
                (this,android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission
                (this,android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
            )
            return
        locationProviderClient.requestLocationUpdates(request,locationCallback,Looper.getMainLooper())

    }
    val locationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            myLocation =p0.lastLocation
            locationProviderClient.removeLocationUpdates(this)
            Serch()
        }

    }


    private fun Serch(){


        thread {
            val sival ="DajG.Y9Y3HD8diIuof5uUuDnkZLrm7zRE/U4jq/xlPX9d9yCi8D8O&format=json&railOprIsttCd=$q&lnCd=$w&stinCd=$e"
            val searchUrl= "https://openapi.kric.go.kr/openapi/convenientInfo/stationToilet?serviceKey=$2a$10$"+sival
            val url=URL(searchUrl)
            val conneckt=url.openConnection() as HttpsURLConnection
            conneckt.requestMethod = "GET"
            conneckt.doInput = true
            conneckt.useCaches = false

            val inputStream = conneckt.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader =BufferedReader(inputStreamReader)
            val builder =StringBuilder()
            while (true){

                val line =bufferedReader.readLine() ?:break
                builder.append(line+"\n")
            }
            runOnUiThread { AlertDialog.Builder(this).setMessage("${builder.toString()}").create().show() }

        }

//        val retrofit=RetrofitHelper.getRetrofitAll("https://openapi.kric.go.kr")
//        val retrofitService =retrofit.create(RetrofitService::class.java)
//        val call = retrofitService.datagetToString("json","S1","3","322")
//       call.enqueue(object :retrofit2.Callback<String>{
//           override fun onResponse(call: Call<String>, response: Response<String>) {
//               val s:String? =response.body()?: return
//               AlertDialog.Builder(this@MainActivity).setMessage("$s").create().show()
//               Log.d("aaa","${s.toString()}")
//           }
//
//           override fun onFailure(call: Call<String>, t: Throwable) {
//               AlertDialog.Builder(this@MainActivity).setMessage("${t.message}").create().show()
//
//           }
//
//       } )

    }
    private fun placeSearch(){


        startLast()
    }
}

