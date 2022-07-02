package com.example.recuirtmentapp

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recuirtmentapp.databinding.FragmentListJobBinding
import com.example.recuirtmentapp.model.Company
import com.example.recuirtmentapp.model.Job

import com.example.recuirtmentapp.util.JobAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_list_job.*


class ListJobFragment : Fragment() {
    private var _binding: FragmentListJobBinding? = null
    private val binding get() = _binding!!
    private val adapter = JobAdapter()
    var uId: String? = null
    lateinit var user: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private lateinit var viewModel: JobViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListJobBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        getUser()
        Log.d("idUser", "$uId")
        setHasOptionsMenu(true)
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback{
            override fun onItemClicked(job: Job) {
                showSelectedJob(job)
            }
        })

        binding.fabAdd.setOnClickListener {
            val intent = Intent(context, AddJob::class.java)

            startActivity(intent)
        }

        binding.apply {
            activity?.let {
                rvJob.layoutManager = LinearLayoutManager(it)
            }
            rvJob.adapter = adapter
            viewModel.job.observe(viewLifecycleOwner, {
                adapter.addJobs(it)
            })


        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_menu) {
            user.signOut()
            val intent = Intent(context, CompanyLogin::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUser() {
        val uRef =
            FirebaseDatabase.getInstance().getReference().child("COMPANY").child(firebaseUser.uid)
        uRef.addValueEventListener(ValueEventListener)
    }

    private val ValueEventListener = object : ValueEventListener {


        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val company = snapshot.getValue<Company>(Company::class.java)
                uId = company!!.id
                viewModel.getJobId(uId!!)


            }
        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    private fun showSelectedJob(job: Job) {
        val i = Job(
            job.id,
            job.jobTitle,
            job.jobType,
            job.companyName,
            job.jobDescription,
            job.jobResponsibilities,
            job.skills,
            job.education,
            job.location,
            job.salary,

            )
        activity?.let {
            val intentWithExtraData = Intent(it, DetailsJob::class.java)
            intentWithExtraData.putExtra(DetailsJob.EXTRA_JOB, i)
            startActivity(intentWithExtraData)
        }


    }
}