package com.example.jobapplicantrecuirtment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobapplicantrecuirtment.databinding.ActivityMainBinding
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FirebaseAuth.getInstance


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = getInstance()
        fStateListener = FirebaseAuth.AuthStateListener {
            val user = firebaseAuth.currentUser
            if (user != null) {
                intent = Intent(this, JobActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
            }
        }


    }
    public override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(fStateListener)


    }

    public override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(fStateListener)
    }
}