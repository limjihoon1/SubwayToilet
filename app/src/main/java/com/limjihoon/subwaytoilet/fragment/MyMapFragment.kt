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
import com.limjihoon.subwaytoilet.R
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
        Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show()

//        binding.btn.setOnClickListener { clickBtnSave() }
//        binding.btn2.setOnClickListener { clickBtnLoad() }

    }
    private fun clickBtnLoad(){



    }


}