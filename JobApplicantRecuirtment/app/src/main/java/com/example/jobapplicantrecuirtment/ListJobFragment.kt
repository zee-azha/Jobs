package com.example.jobapplicantrecuirtment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobapplicantrecuirtment.databinding.FragmentListJobBinding

import com.example.recuirtmentapp.util.JobAdapter
import com.google.firebase.auth.FirebaseAuth


class ListJobFragment : Fragment() {

    private var _binding: FragmentListJobBinding? = null
    private val binding get() = _binding!!
    private val adapter = JobAdapter()
    lateinit var auth: FirebaseAuth
    private lateinit var viewModel: JobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListJobBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback{
            override fun onItemClicked(job: Job) {
                showSelectedJob(job)
            }
        })
        binding.apply {
            activity?.let{
                rvJob.layoutManager = LinearLayoutManager(it)
            }
            rvJob.adapter = adapter
            viewModel.job.observe(viewLifecycleOwner,{
                adapter.addJobs(it)
            })

            viewModel.getRealtimeUpdate()
        }
    }

override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.logout_menu,menu)
    super.onCreateOptionsMenu(menu, inflater)
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if(item.itemId==R.id.logout_menu){
        auth.signOut()
        val intent = Intent(context,MainActivity::class.java)
        startActivity(intent)
    }
    return super.onOptionsItemSelected(item)
}

private fun showSelectedJob(job: Job){
    val i = Job(
        job.id,
        job.jobTitle,
        job.jobType,
        job.companyName,
        job.jobCategory,
        job.jobDescription,
        job.jobResponsibilities,
        job.skills,
        job.education,
        job.location,
        job.salary,

        )
    activity?.let {
        val intentWithExtraData = Intent(it,JobDetailsActivity::class.java)
        intentWithExtraData.putExtra(JobDetailsActivity.EXTRA_JOB, i)
        startActivity(intentWithExtraData)
    }


}


}