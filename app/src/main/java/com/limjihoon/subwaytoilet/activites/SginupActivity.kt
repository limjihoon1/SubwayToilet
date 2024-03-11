package com.limjihoon.subwaytoilet.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.limjihoon.subwaytoilet.databinding.ActivitySginupBinding

class SginupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySginupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySginupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolvar.setNavigationOnClickListener{finish()}
        binding.btnSignup.setOnClickListener { clickBtnSginup() }
    }
    private fun clickBtnSginup(){

        var name =binding.layoutInputId.editText!!.text.toString()
        var pw =binding.layoutInputPw.editText!!.text.toString()
        var pwcom =binding.layoutInputPwcon.editText!!.text.toString()

        if (pw != pwcom){
            AlertDialog.Builder(this).setMessage("비밀번호와 비밀번호 확인이 다릅니다 확인하여 다시 입력해주세요.")
                .create().show()
            binding.layoutInputPw.editText!!.selectAll()
            return
        }

        val userRef: CollectionReference =Firebase.firestore.collection("name")

        userRef.whereEqualTo("name",name).get().addOnSuccessListener {
            if (it.documents.size>0) {
                AlertDialog.Builder(this).setMessage("중복된 이름이 있습니다 확인후 다시 입력해주세요.")
                    .create().show()
                binding.layoutInputId.editText!!.requestFocus()
                binding.layoutInputId.editText!!.selectAll()

            }else{
                val user:MutableMap<String,String> = mutableMapOf()
                user["name"] = name
                user["password"] =pw



                userRef.document().set(user).addOnSuccessListener {

                    AlertDialog.Builder(this).setMessage("축하 합니다 회원가입에 성공 하였습니다.").setPositiveButton("확인",{p1,p2->finish()})
                        .create().show()
                }

            }

        }

    }
}