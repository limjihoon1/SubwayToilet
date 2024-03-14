package com.limjihoon.subwaytoilet.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.limjihoon.subwaytoilet.activites.MainActivity
import com.limjihoon.subwaytoilet.adapter.AdapterLast
import com.limjihoon.subwaytoilet.databinding.ActivityMainBinding
import com.limjihoon.subwaytoilet.databinding.FragmentLastBinding

class LastFragment :Fragment() {

    lateinit var binding: FragmentLastBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLastBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val main :MainActivity = activity as MainActivity
        main.lastData?: return
        binding.re.adapter =AdapterLast(requireContext(),main.lastData!!.adapterData)

    }

}