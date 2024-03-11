package com.limjihoon.subwaytoilet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.limjihoon.subwaytoilet.AData
import com.limjihoon.subwaytoilet.databinding.FragmentReviewSearchBinding

class ReviewSearchFragment:Fragment() {

    lateinit var binding: FragmentReviewSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReviewSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn.setOnClickListener { clickBtn() }

    }

    private fun clickBtn() {
        val name = binding.editTextId.editText!!.text.toString()

        val userRef: CollectionReference = Firebase.firestore.collection("user")
        userRef.whereEqualTo("name", name).get().addOnSuccessListener {

            binding.tvName.text = name
            binding.tvMsg.text = ""

            for (snap in it) {

                val aData: AData? = snap.toObject(AData::class.java)
                aData?.apply {
                    binding.tvMsg.append("$gol \n\n")
                }
            }

        }///
    }
}