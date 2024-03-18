package com.limjihoon.subwaytoilet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.limjihoon.subwaytoilet.R
import com.limjihoon.subwaytoilet.activites.MainActivity
import com.limjihoon.subwaytoilet.databinding.FragmentMyMapBinding

public class MyMapFragment :Fragment(){

    lateinit var binding:FragmentMyMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyMapBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.map.start(mapShow)

    }
    private val mapShow:KakaoMapReadyCallback = object :KakaoMapReadyCallback(){
        override fun onMapReady(kakaoMap: KakaoMap) {
            val latitude:Double = (activity as MainActivity).myLocation?.latitude?:37.555
            val longitude:Double = (activity as MainActivity).myLocation?.longitude?:126.9746
            val mypos:LatLng=LatLng.from(latitude,longitude)

            val cameraUpdate =CameraUpdateFactory.newCenterPosition(mypos,16)
            kakaoMap.moveCamera(cameraUpdate)

            val labelOptions=LabelOptions.from(mypos).setStyles(R.drawable.profle)
            val labelOptionsLayout =kakaoMap.labelManager!!.layer!!
            labelOptionsLayout.addLabel(labelOptions)

//            val placeLists:List<>
        }


    }


}