package com.example.jobapplicantrecuirtment

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.jobapplicantrecuirtment.databinding.ActivityApplyJobBinding
import com.example.jobapplicantrecuirtment.model.Users

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ApplyJobActivity : AppCompatActivity() {
    lateinit var binding: ActivityApplyJobBinding
    lateinit var user: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    var uId: String? = null
    private lateinit var viewModel: ApplicationViewModel
    var filepath: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        getUser()
        viewModel = ViewModelProviders.of(this).get(ApplicationViewModel::class.java)
        binding.postjob.setOnClickListener {
            val phone = binding.phoneNumber.text.toString().trim()
            if (phone.isEmpty()||!Patterns.PHONE.matcher(phone).matches()) {
                binding.phoneNumber.error = "Phone required"
                binding.phoneNumber.requestFocus()
                return@setOnClickListener
            }
            chooseDoc()


        }

    }
    private fun chooseDoc() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent,100)
        Log.d("success", "sucssed add ${filepath?.path}")

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK ) {

            if (data?.data != null) {
                filepath = data.data
                UploadFile()

            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatDate(dateObject: Date): String? {
        val timeFormat = SimpleDateFormat("yyyyMMddhh:mmssa")
        return timeFormat.format(dateObject)
    }
    private fun UploadFile() {
        if(filepath == null) return
        val dateObject = Date(System.currentTimeMillis())
        val dateFormat = formatDate(dateObject)
        val jobId = intent.getStringExtra("JobId")
        val name = binding.Name.text.toString().trim()
        val email = binding.edmail.text.toString().trim()
        val phone = binding.phoneNumber.text.toString().trim()
        val application= com.example.jobapplicantrecuirtment.model.Application()
        val filename = name
        val sref = FirebaseStorage.getInstance().getReference("/Application/"+jobId+"-"+dateFormat+"_"+filename)
        sref.putFile(filepath!!)
            .addOnSuccessListener {

                application.jobId = jobId
                application.userId = uId
                application.username = name
                application.email = email
                application.phoneNumber = phone
                application.cv = it.toString()
                viewModel.addApply(application)

                finish()

                Toast.makeText(this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show()
                Log.d("sucsess", "succsess${filepath?.path}")

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("failed", "failed${filepath?.path}")
            }


    }
    private fun getUser(){
        val uRef = FirebaseDatabase.getInstance().getReference().child("USER").child(firebaseUser.uid)
        uRef.addValueEventListener(ValueEventListener)
    }

    private val ValueEventListener = object  : ValueEventListener {




        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                val users = snapshot.getValue<Users>(Users::class.java)
                uId = users!!.id
                binding.Name.setText(users!!.userNama)
                binding.edmail.setText(firebaseUser.email)

            }
        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }



}