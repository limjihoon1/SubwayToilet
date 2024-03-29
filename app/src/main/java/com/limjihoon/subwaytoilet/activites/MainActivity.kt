package com.limjihoon.subwaytoilet.activites

import android.content.pm.PackageManager
import android.location.Location
import android.net.DnsResolver.Callback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
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
import com.limjihoon.subwaytoilet.data.alldatapl
import com.limjihoon.subwaytoilet.databinding.ActivityMainBinding
import com.limjihoon.subwaytoilet.fragment.LastFragment
import com.limjihoon.subwaytoilet.fragment.MyMapFragment
import com.limjihoon.subwaytoilet.fragment.ReviewFragment
import com.limjihoon.subwaytoilet.fragment.ReviewSearchFragment
import com.limjihoon.subwaytoilet.network.RetrofitHelper
import com.limjihoon.subwaytoilet.network.RetrofitService
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var myLocation:Location?=null
    val locationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }



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
//        Toast.makeText(this, "$dataall\n" +"${myLocation?.longitude} ,${myLocation?.latitude}", Toast.LENGTH_SHORT).show()
//        val retrofit = RetrofitHelper.getRetrofitAll("oiletopenapi/convenientInfo/stationT?serviceKey=")
//        val requestServices = retrofit.create(RetrofitService::class.java)
//        val call =requestServices.dataget("A1","AR","A01")
//        call.enqueue(object :retrofit2.Callback<alldatapl>{
//            override fun onResponse(call: Call<alldatapl>, response: Response<alldatapl>) {
//                val dd = response.body()
//                AlertDialog.Builder(this@MainActivity).setMessage("$dd").create().show()
//
//            }
//
//            override fun onFailure(call: Call<alldatapl>, t: Throwable) {
//                AlertDialog.Builder(this@MainActivity).setMessage("실패~").create().show()
//            }
//
//        })

    }
    var dataall =""

}