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

import com.limjihoon.subwaytoilet.R
import com.limjihoon.subwaytoilet.data.Accc
import com.limjihoon.subwaytoilet.data.AdapterData
import com.limjihoon.subwaytoilet.data.StationDataSearch

import com.limjihoon.subwaytoilet.databinding.ActivityMainBinding
import com.limjihoon.subwaytoilet.fragment.LastFragment
import com.limjihoon.subwaytoilet.fragment.MyMapFragment
import com.limjihoon.subwaytoilet.fragment.ReviewFragment
import com.limjihoon.subwaytoilet.fragment.ReviewSearchFragment
import org.json.JSONArray
import java.io.BufferedReader
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
    var lastData:Accc? =null


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


    var quit:MutableList<StationDataSearch> = mutableListOf()
    var sivar :MutableList<AdapterData> = mutableListOf()
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
                AlertDialog.Builder(this)
                    .setMessage(
                        "${quit.get(i).RAIL_OPR_ISTT_CD}    ${quit.get(i).LN_CD}    ${
                            quit.get(
                                i
                            ).STIN_CD
                        }    ${quit.get(i).STIN_NM}"
                    )
                    .create().show()
            } else {
                continue@loop
            }
            thread {
                val addUrl = "DajG.Y9Y3HD8diIuof5uUuDnkZLrm7zRE/U4jq/xlPX9d9yCi8D8O&format=json&railOprIsttCd=${quit.get(i).RAIL_OPR_ISTT_CD}&lnCd=${quit.get(i).LN_CD}&stinCd=${quit.get(i).STIN_CD}"
                val searchUrl = "https://openapi.kric.go.kr/openapi/convenientInfo/stationToilet?serviceKey=$2a$10$"+addUrl
                val url =URL(searchUrl)
                val connect = url.openConnection() as HttpsURLConnection
                connect.requestMethod = "GET"
                connect.doInput = true
                connect.useCaches = false
                val inputStream2 = connect.inputStream
                val inputStreamReader2 = InputStreamReader(inputStream2)
                val bufferedReader =BufferedReader(inputStreamReader2)

                val builder = StringBuilder()
                while (true){
                    val line =bufferedReader.readLine() ?:break
                    builder.append(line +"\n")
                }
//                val jsonArray: JSONArray = JSONArray(builder.toString())
//                for (i in 0 until jsonArray.length()) {
//                    val jo = jsonArray.getJSONObject(i)
//
//                    var railOprIsttCd = jo.getString("railOprIsttCd")
//                    var lnCd = jo.getString("lnCd")
//                    var stinCd = jo.getString("stinCd")
//                    var grndDvNm = jo.getString("grndDvNm")
//                    var stinFlor = jo.getInt("stinFlor")
//                    var gateInotDvNm = jo.getString("gateInotDvNm")
//                    var exitNo = jo.getString("exitNo")
//                    var dtlLoc = jo.getString("dtlLoc")
//                    var mlFmlDvNm = jo.getString("mlFmlDvNm")
//                    var toltNum = jo.getInt("toltNum")
//                    var diapExchNum = jo.getString("diapExchNum")
//
//
//                    val show = AdapterData(railOprIsttCd,lnCd,stinCd,grndDvNm,stinFlor,gateInotDvNm,exitNo,dtlLoc,mlFmlDvNm,toltNum,diapExchNum)
//                    sivar.add(show)
//
//                }
                Log.d("sivar",sivar.get(0).diapExchNum)

                runOnUiThread { AlertDialog.Builder(this).setMessage("${builder.toString()}").create().show()}
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,LastFragment()).commit()

            }

        }

    }





    private fun placeSearch(){
        startLast()
    }
}

