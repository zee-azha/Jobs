package com.example.recuirtmentapp

import android.app.Application
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.recuirtmentapp.databinding.ActivityApplicationDetailsBinding
import com.example.recuirtmentapp.model.CV
import com.example.recuirtmentapp.util.NODE_Apply
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import kotlinx.android.synthetic.main.activity_application_details.*


class ApplicationDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityApplicationDetailsBinding
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var ref: StorageReference
    var application: String? = null
    var name: String? = null
    var email: String? = null

    companion object{
        const val  EXTRA_CV = "extra_cv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseStorage = FirebaseStorage.getInstance()
        val cv = intent.getParcelableExtra<CV>(EXTRA_CV) as CV
        application = cv.cv.toString()
        name = cv.username
        val phone = cv.phoneNumber
        email = cv.email



        binding.apply {
            Name.text = name.toString()
            edmail.text = email.toString()
            phoneNumber.text = phone.toString()
        }


        binding.downjob.setOnClickListener{
        downloadCv()

        }
        Log.d("filedoc","$application")
    }

    fun downloadCv(){




            downloadFile(this,"$name+$email","pdf",Environment.DIRECTORY_DOWNLOADS,application.toString())



    }
    fun downloadFile(
        context: Context,
        filename: String,
        fileExtension: String,
        destiantionDirectorty: String?,
        url: String?
    ) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(this, destiantionDirectorty, filename + fileExtension)
        downloadManager.enqueue(request)
    }


}



