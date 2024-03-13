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
        var r=binding.et.text?.toString()
        if (r==null){
            q="s1"
            w="3"
            e="322"
        }else if (r=="서울역"){
            q="AR"
            w="A1"
            e="A01"
        } else if (r=="공덕역"){
            q="AR"
            w="A1"
            e="A02"
        }else if (r=="공덕"){
            q="AR"
            w="A1"
            e="A02"
        }else if (r=="홍대입구"){
            q="AR"
            w="A1"
            e="A03"
            //////////////////////
        }else if (r=="디지털미티어시티"){
            q="AR"
            w="A1"
            e="A04"
        }else if (r=="마곡나루"){
            q="AR"
            w="A1"
            e="A042"
        }else if (r=="김포공항"){
            q="AR"
            w="A1"
            e="A05"
        }else if (r=="계양") {
            q = "AR"
            w = "A1"
            e = "A06"
        }else if (r=="검앙"){
            q="AR"
            w="A1"
            e="A07"
        }else if (r=="청라국제도시"){
            q="AR"
            w="A1"
            e="A071"
        }else if (r=="영종"){
            q="AR"
            w="A1"
            e="A072"
        }else if (r=="운서"){
            q="AR"
            w="A1"
            e="A08"
        }else if (r=="공항화물청사"){
            q="AR"
            w="A1"
            e="A09"
        }else if (r=="인천공항1터미널"){
            q="AR"
            w="A1"
            e="A10"
        }else if (r=="인천공한2터미널"){
            q="AR"
            w="A1"
            e="A11"
        }else if (r=="논현"){
            q="DX"
            w="D1"
            e="4305"
        }else if (r=="신논현"){
            q="DX"
            w="D1"
            e="4306"
        }else if (r=="강남"){
            q="DX"
            w="D1"
            e="4307"
        }else if (r=="양재"){
            q="DX"
            w="D1"
            e="4308"
        }else if (r=="양재시민의숲"){
            q="DX"
            w="D1"
            e="4309"
        }else if (r=="청계산입구"){
            q="DX"
            w="D1"
            e="4310"
        }else if (r=="판교"){
            q="DX"
            w="D1"
            e="4311"
        }else if (r=="정자"){
            q="DX"
            w="D1"
            e="4312"
        }else if (r=="성북"){
            q="DX"
            w="D1"
            e="4316"
        }else if (r=="상현"){
            q="DX"
            w="D1"
            e="4317"
        }else if (r=="광교중앙"){
            q="DX"
            w="D1"
            e="4318"
        }else if (r=="광교"){
            q="DX"
            w="D1"
            e="4319"
        }else if (r=="신사"){
            q="DX"
            w="D1"
            e="D04"
        }else if (r=="미금"){
            q="DX"
            w="D1"
            e="D13"
        }else if (r=="동천"){
            q="DX"
            w="D1"
            e="D14"
        }else if (r=="수지구청"){
            q="DX"
            w="D1"
            e="D15"
        }else if (r=="기흥"){
            q="EV"
            w="E1"
            e="Y110"
        }else if (r=="강남대"){
            q="EV"
            w="E1"
            e="Y111"
        }else if (r=="지석"){
            q="EV"
            w="E1"
            e="Y112"
        }else if (r=="어정"){
            q="EV"
            w="E1"
            e="Y113"
        }else if (r=="동백"){
            q="EV"
            w="E1"
            e="Y114"
        }else if (r=="초당"){
            q="EV"
            w="E1"
            e="Y115"
        }else if (r=="삼가"){
            q="EV"
            w="E1"
            e="Y116"
        }else if (r=="용인대"){
            q="EV"
            w="E1"
            e="Y117"
        }else if (r=="시청용인대") {
            q = "EV"
            w = "E1"
            e = "Y117"
        }else if (r=="시청 용인대") {
            q = "EV"
            w = "E1"
            e = "Y117"
        }else if (r=="명지대"){
            q="EV"
            w="E1"
            e="Y118"
        }else if (r=="김량장"){
            q="EV"
            w="E1"
            e="Y119"
        }else if (r=="운동장송담대"){
            q="EV"
            w="E1"
            e="Y120"
        }else if (r=="운동장 송담대"){
            q="EV"
            w="E1"
            e="Y120"
        }else if (r=="송담대"){
            q="EV"
            w="E1"
            e="Y120"
        }else if (r=="고진"){
            q="EV"
            w="E1"
            e="Y121"
        }else if (r=="보평"){
            q="EV"
            w="E1"
            e="Y122"
        }else if (r=="둔전"){
            q="EV"
            w="E1"
            e="Y123"
        }else if (r=="전대"){
            q="EV"
            w="E1"
            e="Y124"
        }else if (r=="에버랜드"){
            q="EV"
            w="E1"
            e="Y124"
        }else if (r=="전대 에버랜드"){
            q="EV"
            w="E1"
            e="Y124"
        }else if (r=="전대애버랜드"){
            q="EV"
            w="E1"
            e="Y124"
        }else if (r=="양촌"){
            q="GM"
            w="G1"
            e="G100"
        }else if (r=="구래"){
            q="GM"
            w="G1"
            e="G101"
        }else if (r=="마산"){
            q="GM"
            w="G1"
            e="G102"
        }else if (r=="장기"){
            q="GM"
            w="G1"
            e="G103"
        }else if (r=="운양"){
            q="GM"
            w="G1"
            e="G104"
        }else if (r=="걸포북변"){
            q="GM"
            w="G1"
            e="G105"
        }else if (r=="사우"){
            q="GM"
            w="G1"
            e="G106"
        }else if (r=="김포시청"){
            q="GM"
            w="G1"
            e="G106"
        }else if (r=="풍무"){
            q="GM"
            w="G1"
            e="G107"
        }else if (r=="고촌"){
            q="GM"
            w="G1"
            e="G108"
        }else if (r=="김포공항 용인"){
            q="GM"
            w="G1"
            e="G109"
        }else if (r=="인천공항1터미널 자기부상"){
            q="IA"
            w="M1"
            e="G101"
        }else if (r=="장기주차장"){
            q="IA"
            w="M1"
            e="G102"
        }else if (r=="합동청사"){
            q="IA"
            w="M1"
            e="G103"
        }else if (r=="파라다이스시티"){
            q="IA"
            w="M1"
            e="G104"
        }else if (r=="워터파크"){
            q="IA"
            w="M1"
            e="G105"
        }else if (r=="용유"){
            q="IA"
            w="M1"
            e="G106"
        }else if (r=="까치울"){
            q="IC"
            w="7"
            e="751"
        }else if (r=="부천종합운동장"){
            q="IC"
            w="7"
            e="752"
        }else if (r=="춘의"){
            q="IC"
            w="7"
            e="753"
        }else if (r=="신중동"){
            q="IC"
            w="7"
            e="754"
        }else if (r=="부천시청"){
            q="IC"
            w="7"
            e="755"
        }else if (r=="상동"){
            q="IC"
            w="7"
            e="756"
        }else if (r=="삼산체육관"){
            q="IC"
            w="7"
            e="757"
        }else if (r=="굴포천"){
            q="IC"
            w="7"
            e="758"
        }else if (r=="부평구청"){
            q="IC"
            w="7"
            e="759"
        }else if (r=="산곡"){
            q="IC"
            w="7"
            e="760"
        }else if (r=="석남"){
            q="IC"
            w="7"
            e="761"
        }else if (r=="인천계양"){
            q="IC"
            w="I1"
            e="110"
        }else if (r=="귤현"){
            q="IC"
            w="I1"
            e="111"
        }else if (r=="박촌"){
            q="IC"
            w="I1"
            e="112"
        }else if (r=="임학"){
            q="IC"
            w="I1"
            e="113"
        }else if (r=="계산"){
            q="IC"
            w="I1"
            e="114"
        }else if (r=="경인교대입구"){
            q="IC"
            w="I1"
            e="115"
        }else if (r=="작전"){
            q="IC"
            w="I1"
            e="116"
        }else if (r=="갈산"){
            q="IC"
            w="I1"
            e="117"
        }else if (r=="인천부평구청"){
            q="IC"
            w="I1"
            e="118"
        }else if (r=="부평시장"){
            q="IC"
            w="I1"
            e="119"
        }else if (r=="부평"){
            q="IC"
            w="I1"
            e="120"
        }else if (r=="동수"){
            q="IC"
            w="I1"
            e="121"
        }else if (r=="부평삼거리"){
            q="IC"
            w="I1"
            e="122"
        }else if (r=="간석오거리"){
            q="IC"
            w="I1"
            e="123"
        }else if (r=="인천시청"){
            q="IC"
            w="I1"
            e="124"
        }else if (r=="예술회관"){
            q="IC"
            w="I1"
            e="125"
        }else if (r=="인천터미널"){
            q="IC"
            w="I1"
            e="126"
        }else if (r=="문학경기장"){
            q="IC"
            w="I1"
            e="127"
        }else if (r=="선학"){
            q="IC"
            w="I1"
            e="128"
        }else if (r=="신연수"){
            q="IC"
            w="I1"
            e="129"
        }else if (r=="원인재"){
            q="IC"
            w="I1"
            e="130"
        }else if (r=="동춘"){
            q="IC"
            w="I1"
            e="131"
        }else if (r=="동막"){
            q="IC"
            w="I1"
            e="132"
        }else if (r=="캠퍼스타운"){
            q="IC"
            w="I1"
            e="133"
        }else if (r=="테크노파크"){
            q="IC"
            w="I1"
            e="134"
        }else if (r=="지식정보단지"){
            q="IC"
            w="I1"
            e="135"
        }else if (r=="인천대입구"){
            q="IC"
            w="I1"
            e="136"
        }else if (r=="샌트럴파크"){
            q="IC"
            w="I1"
            e="137"
        }else if (r=="국제업무지구"){
            q="IC"
            w="I1"
            e="138"
        }else if (r=="송도달빛축제공원"){
            q="IC"
            w="I1"
            e="139"
        }else if (r=="검단오류"){
            q="IC"
            w="I2"
            e="201"
        }else if (r=="왕길"){
            q="IC"
            w="I2"
            e="202"
        }else if (r=="검단사거리"){
            q="IC"
            w="I2"
            e="203"
        }else if (r=="마전"){
            q="IC"
            w="I2"
            e="204"
        }else if (r=="완정"){
            q="IC"
            w="I2"
            e="205"
        }else if (r=="독정"){
            q="IC"
            w="I2"
            e="206"
        }else if (r=="검암"){
            q="IC"
            w="I2"
            e="207"
        }else if (r=="검바위"){
            q="IC"
            w="I2"
            e="208"
        }else if (r=="아시아드경기장"){
            q="IC"
            w="I2"
            e="209"
        }else if (r=="서구청"){
            q="IC"
            w="I2"
            e="210"
        }else if (r=="가정"){
            q="IC"
            w="I2"
            e="211"
        }else if (r=="가정중앙시장"){
            q="IC"
            w="I2"
            e="212"
        }else if (r=="인천석남"){
            q="IC"
            w="I2"
            e="213"
        }else if (r=="서부여성회관"){
            q="IC"
            w="I2"
            e="214"
        }else if (r=="인천가좌"){
            q="IC"
            w="I2"
            e="215"
        }else if (r=="가재울"){
            q="IC"
            w="I2"
            e="216"
        }else if (r=="주안국가산단"){
            q="IC"
            w="I2"
            e="217"
        }else if (r=="주안"){
            q="IC"
            w="I2"
            e="218"
        }else if (r=="시민공원"){
            q="IC"
            w="I2"
            e="219"
        }else if (r=="석바위시장"){
            q="IC"
            w="I2"
            e="220"
        }else if (r=="인천시청2"){
            q="IC"
            w="I2"
            e="221"
        }else if (r=="석천사거리"){
            q="IC"
            w="I2"
            e="222"
        }else if (r=="모래내시장"){
            q="IC"
            w="I2"
            e="223"
        }else if (r=="만수"){
            q="IC"
            w="I2"
            e="224"
        }else if (r=="남동구청"){
            q="IC"
            w="I2"
            e="225"
        }else if (r=="인천대공원"){
            q="IC"
            w="I2"
            e="226"
        }else if (r=="운연"){
            q="IC"
            w="I2"
            e="227"
        }else if (r=="지행"){
            q="KR"
            w="1"
            e="0104"
        }else if (r=="도봉"){
            q="KR"
            w="1"
            e="0114"
        }else if (r=="창동"){
            q="KR"
            w="1"
            e="0116"
        }else if (r=="월계"){
            q="KR"
            w="1"
            e="0118"
        }else if (r=="외대앞"){
            q="KR"
            w="1"
            e="0122"
        }else if (r=="대방"){
            q="KR"
            w="1"
            e="0137"
        }else if (r=="신길"){
            q="KR"
            w="1"
            e="0138"
        }else if (r=="덕정"){
            q="KR"
            w="1"
            e="0141"
        }else if (r=="구일"){
            q="KR"
            w="1"
            e="0142"
        }else if (r=="온수"){
            q="KR"
            w="1"
            e="0145"
        }else if (r=="역곡"){
            q="KR"
            w="1"
            e="0146"
        }else if (r=="송내"){
            q="KR"
            w="1"
            e="0150"
        }else if (r=="부평서울1호선"){
            q="KR"
            w="1"
            e="0152"
        }else if (r=="성균관대"){
            q="KR"
            w="1"
            e="0153"
        }else if (r=="연천"){
            q="KR"
            w="1"
            e="100-3"
        }else if (r=="전곡"){
            q="KR"
            w="1"
            e="100-2"
        }else if (r=="청산"){
            q="KR"
            w="1"
            e="100-1"
        }else if (r=="소요산"){
            q="KR"
            w="1"
            e="100"
        }else if (r=="동두천"){
            q="KR"
            w="1"
            e="101"
        }else if (r=="보산"){
            q="KR"
            w="1"
            e="102"
        }else if (r=="동두천중앙"){
            q="KR"
            w="1"
            e="103"
        }else if (r=="덕계"){
            q="KR"
            w="1"
            e="106"
        }else if (r=="녹양"){
            q="KR"
            w="1"
            e="108"
        }else if (r=="가능"){
            q="KR"
            w="1"
            e="109"
        }else if (r=="의정부"){
            q="KR"
            w="1"
            e="110"
        }else if (r=="회룡"){
            q="KR"
            w="1"
            e="111"
        }else if (r=="망월사"){
            q="KR"
            w="1"
            e="112"
        }else if (r=="도봉산"){
            q="KR"
            w="1"
            e="113"
        }else if (r=="방학"){
            q="KR"
            w="1"
            e="115"
        }else if (r=="녹천"){
            q="KR"
            w="1"
            e="117"
        }else if (r=="석계"){
            q="KR"
            w="1"
            e="120"
        }else if (r=="신이문"){
            q="KR"
            w="1"
            e="121"
        }else if (r=="회기"){
            q="KR"
            w="1"
            e="123"
        }else if (r=="남영"){
            q="KR"
            w="1"
            e="134"
        }else if (r=="용산"){
            q="KR"
            w="1"
            e="135"
        }else if (r=="노량진"){
            q="KR"
            w="1"
            e="136"
        }else if (r=="영등포"){
            q="KR"
            w="1"
            e="139"
        }else if (r=="신도림"){
            q="KR"
            w="1"
            e="140"
        }else if (r=="개봉"){
            q="KR"
            w="1"
            e="143"
        }else if (r=="오류동"){
            q="KR"
            w="1"
            e="144"
        }else if (r=="소사"){
            q="KR"
            w="1"
            e="147"
        }else if (r=="부천"){
            q="KR"
            w="1"
            e="148"
        }else if (r=="명학"){
            q="KR"
            w="1"
            e="P148"
        }else if (r=="부개"){
            q="KR"
            w="1"
            e="151"
        }else if (r=="백운"){
            q="KR"
            w="1"
            e="153"
        }else if (r=="동암"){
            q="KR"
            w="1"
            e="154"
        }else if (r=="주안서울1호선"){
            q="KR"
            w="1"
            e="156"
        }else if (r=="도화"){
            q="KR"
            w="1"
            e="157"
        }else if (r=="제물포"){
            q="KR"
            w="1"
            e="158"
        }else if (r=="도원"){
            q="KR"
            w="1"
            e="159"
        }else if (r=="동인천"){
            q="KR"
            w="1"
            e="160"
        }else if (r=="인천서울1호선"){
            q="KR"
            w="1"
            e="161"
        }else if (r=="세류"){
            q="KR"
            w="1"
            e="1715"
        }else if (r=="중동"){
            q="KR"
            w="1"
            e="1822"
        }else if (r=="광운대"){
            q="KR"
            w="1"
            e="199"
        }else if (r=="양주"){
            q="KR"
            w="1"
            e="K107"
        }else if (r=="광명"){
            q="KR"
            w="1"
            e="K410"
        }else if (r=="구로"){
            q="KR"
            w="1"
            e="P141"
        }else if (r=="가산디지털단지"){
            q="KR"
            w="1"
            e="P142"
        }else if (r=="독산"){
            q="KR"
            w="1"
            e="P143"
        }else if (r=="금천구청"){
            q="KR"
            w="1"
            e="P144"
        }else if (r=="석수"){
            q="KR"
            w="1"
            e="P145"
        }else if (r=="관악"){
            q="KR"
            w="1"
            e="P146"
        }else if (r=="안양"){
            q="KR"
            w="1"
            e="P147"
        }else if (r=="금정"){
            q="KR"
            w="1"
            e="P149"
        }else if (r=="군포"){
            q="KR"
            w="1"
            e="P150"
        }else if (r=="당정"){
            q="KR"
            w="1"
            e="P151"
        }else if (r=="의왕"){
            q="KR"
            w="1"
            e="P152"
        }else if (r=="화서"){
            q="KR"
            w="1"
            e="P154"
        }else if (r=="수원"){
            q="KR"
            w="1"
            e="P155"
        }else if (r=="병점"){
            q="KR"
            w="1"
            e="P157"
        }else if (r=="서동탄"){
            q="KR"
            w="1"
            e="P157-2"
        }else if (r=="세마"){
            q="KR"
            w="1"
            e="P158"
        }else if (r=="오산대"){
            q="KR"
            w="1"
            e="P159"
        }else if (r=="오산"){
            q="KR"
            w="1"
            e="P160"
        }else if (r=="진위"){
            q="KR"
            w="1"
            e="P161"
        }else if (r=="송탄"){
            q="KR"
            w="1"
            e="P162"
        }else if (r=="서정리"){
            q="KR"
            w="1"
            e="P163"
        }else if (r=="평택지제"){
            q="KR"
            w="1"
            e="P164"
        }else if (r=="평택"){
            q="KR"
            w="1"
            e="P165"
        }else if (r=="성환"){
            q="KR"
            w="1"
            e="P166"
        }else if (r=="직산"){
            q="KR"
            w="1"
            e="P167"
        }else if (r=="두정"){
            q="KR"
            w="1"
            e="P168"
        }else if (r=="천안서울1호선"){
            q="KR"
            w="1"
            e="P169"
        }else if (r=="봉명"){
            q="KR"
            w="1"
            e="P170"
        }else if (r=="쌍용"){
            q="KR"
            w="1"
            e="P171"
        }else if (r=="아산"){
            q="KR"
            w="1"
            e="P172"
        }else if (r=="배방"){
            q="KR"
            w="1"
            e="P174"
        }else if (r=="온양온천"){
            q="KR"
            w="1"
            e="P176"
        }else if (r=="신창"){
            q="KR"
            w="1"
            e="P177"
        }else if (r=="탕정"){
            q="KR"
            w="1"
            e="P173"
        }else if (r=="대화"){
            q="KR"
            w="3"
            e="0309"
        }else if (r=="대곡"){
            q="KR"
            w="3"
            e="0314"
        }else if (r=="화정"){
            q="KR"
            w="3"
            e="0315"
        }else if (r=="원흥"){
            q="KR"
            w="3"
            e="1948"
        }else if (r=="주엽"){
            q="KR"
            w="3"
            e="310"
        }else if (r=="정발산"){
            q="KR"
            w="3"
            e="311"
        }else if (r=="마두"){
            q="KR"
            w="3"
            e="312"
        }else if (r=="백석"){
            q="KR"
            w="3"
            e="313"
        }else if (r=="원당"){
            q="KR"
            w="3"
            e="316"
        }else if (r=="삼송"){
            q="KR"
            w="3"
            e="318"
        }else if (r=="대공원"){
            q="KR"
            w="4"
            e="0437"
        }else if (r=="과천"){
            q="KR"
            w="4"
            e="0438"
        }else if (r=="인덕원"){
            q="KR"
            w="4"
            e="0440"
        }else if (r=="범계"){
            q="KR"
            w="4"
            e="0442"
        }else if (r=="대야미"){
            q="KR"
            w="4"
            e="0446"
        }else if (r=="반월"){
            q="KR"
            w="4"
            e="0447"
        }else if (r=="정부과천청사"){
            q="KR"
            w="4"
            e="0509"
        }else if (r=="한대앞"){
            q="KR"
            w="4"
            e="1755"
        }else if (r=="선바위"){
            q="KR"
            w="4"
            e="435"
        }else if (r=="경마공원"){
            q="KR"
            w="4"
            e="436"
        }else if (r=="평촌"){
            q="KR"
            w="4"
            e="441"
        }else if (r=="산본"){
            q="KR"
            w="4"
            e="444"
        }else if (r=="수리산"){
            q="KR"
            w="4"
            e="445"
        }else if (r=="상록수"){
            q="KR"
            w="4"
            e="448"
        }else if (r=="중앙"){
            q="KR"
            w="4"
            e="K252"
        }else if (r=="초지"){
            q="KR"
            w="4"
            e="K254"
        }else if (r=="안산"){
            q="KR"
            w="4"
            e="K255"
        }else if (r=="신길온천"){
            q="KR"
            w="4"
            e="K256"
        }else if (r=="정왕"){
            q="KR"
            w="4"
            e="K257"
        }else if (r=="오이도"){
            q="KR"
            w="4"
            e="K258"
        }else if (r=="금정"){
            q="KR"
            w="4"
            e="P149"
        }else if (r=="고잔"){
            q="KR"
            w="4"
            e="K253"
        }else if (r=="수내"){
            q="KR"
            w="K1"
            e="1856"
        }else if (r=="오목천"){
            q="KR"
            w="K1"
            e="1874"
        }else if (r=="송도"){
            q="KR"
            w="K1"
            e="1886"
        }else if (r=="인천수인분당선"){
            q="KR"
            w="K1"
            e="K0272"
        }else if (r=="청량리"){
            q="KR"
            w="K1"
            e="K209"
//        }else if (r=="압구로데오"){
//            q="KR"
//            w="K1"
//            e="K212"
//        }else if (r=="선정"){
//            q="KR"
//            w="K1"
//            e="K214"
//        }else if (r=="선릉"){
//            q="KR"
//            w="K1"
//            e="K215"
//        }else if (r=="개포동"){
//            q="KR"
//            w="K1"
//            e="K219"
//        }else if (r=="복정"){
//            q="KR"
//            w="K1"
//            e="K222"
        }



        startLast()
    }
}

