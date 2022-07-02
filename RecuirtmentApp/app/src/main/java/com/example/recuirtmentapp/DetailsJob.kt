package com.example.recuirtmentapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.recuirtmentapp.databinding.ActivityDetailsJobBinding
import com.example.recuirtmentapp.databinding.FragmentApplicationListBinding
import com.example.recuirtmentapp.databinding.FragmentListJobBinding
import com.example.recuirtmentapp.model.Job

class DetailsJob : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsJobBinding

    companion object{
        const val  EXTRA_JOB = "extra_job"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_job)
        binding = ActivityDetailsJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val listFragment = ApplicationListFragment()
        val job = intent.getParcelableExtra<Job>(EXTRA_JOB) as Job
        val jobId = job.id
        var jobTitle = job.jobTitle
        var jobCompany = job.companyName
        var jobDescription = job.jobDescription
        var jobResponsibilities = job.jobResponsibilities
        var jobSkill = job.skills
        var jobEdu = job.education
        var jobSalary = job.salary
        var jobLocation = job.location
        var jobType = job.jobType




        binding.apply {
            jobtitle.text = jobTitle.toString()
            company.text = jobCompany.toString()
            jobdescription.text = jobDescription.toString()
            jobResp.text = jobResponsibilities.toString()
            skills.text= jobSkill.toString()
            education.text = jobEdu.toString()
            joblocation.text = jobLocation.toString()
            salary.text = jobSalary.toString()
            jobtype.text = jobType.toString()


        }
        binding.application.setOnClickListener {
           val bundle = Bundle()
            bundle.putString("jobId",jobId)
            listFragment.arguments = bundle
            fragmentTransaction.add(R.id.container,listFragment).commit()

        }
    }

}