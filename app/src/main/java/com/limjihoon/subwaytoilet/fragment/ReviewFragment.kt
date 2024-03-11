package com.limjihoon.subwaytoilet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.limjihoon.subwaytoilet.AData
import com.limjihoon.subwaytoilet.databinding.FragmentReviewBinding

class ReviewFragment :Fragment(){
    lateinit var binding: FragmentReviewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show()

        binding.btn.setOnClickListener { clickBtnSave() }


    }
    private fun clickBtnSave(){
        var datass:MutableMap<String,String> = mutableMapOf()
        datass["name"]=binding.inputLayout.editText!!.text.toString()

        datass["gol"]=binding.inputLayoutTh.editText!!.text.toString()

        val userRef:CollectionReference =Firebase.firestore.collection("user")
        userRef.document().set(datass).addOnSuccessListener { context?.let { it1 ->
            AlertDialog.Builder(
                it1
            ).setMessage("성공").create().show()

        }
        }


    }

}