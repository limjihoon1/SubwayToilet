package com.limjihoon.subwaytoilet.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



import com.limjihoon.subwaytoilet.G
import com.limjihoon.subwaytoilet.data.UserAccount
import com.limjihoon.subwaytoilet.databinding.ActivityLogonBinding

class LogonActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolvar.setNavigationOnClickListener{finish()}

        binding.btnLogind.setOnClickListener { clickBtn() }
        binding.btnSignup.setOnClickListener { clickBtn2() }
    }
    private fun clickBtn(){
        val name = binding.layoutInputId.editText!!.text.toString()
        val pw = binding.layoutInputPw.editText!!.text.toString()

        val userRef: CollectionReference = Firebase.firestore.collection("name")
        userRef.whereEqualTo("name",name).whereEqualTo("password",pw).get().addOnSuccessListener {
            if (it.documents.size>0){

                val id =it.documents[0].id
                G.userAccount= UserAccount(id,name)
                var intent =Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }else{
                AlertDialog.Builder(this).setMessage("존재하지 않는 이름,혹은 비밀번호가 틀렸습니다 확인후 다시 입력해주세요")
                    .create().show()
                binding.layoutInputId.editText!!.requestFocus()
                binding.layoutInputId.editText!!.selectAll()
            }

        }

    }

    private fun clickBtn2(){
        startActivity(Intent(this,SginupActivity::class.java))
        finish()
    }
}