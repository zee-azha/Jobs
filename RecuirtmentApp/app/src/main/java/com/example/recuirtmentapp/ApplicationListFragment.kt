package com.example.recuirtmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recuirtmentapp.databinding.FragmentApplicationListBinding
import com.example.recuirtmentapp.model.CV
import com.example.recuirtmentapp.model.Job
import com.example.recuirtmentapp.util.ApplicationAdapter
import com.example.recuirtmentapp.util.JobAdapter


class ApplicationListFragment : Fragment() {

    private var _binding: FragmentApplicationListBinding? = null
    private val binding get() = _binding!!
    private val adapter = ApplicationAdapter()

    private lateinit var viewModel: ApplicationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentApplicationListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ApplicationViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val jobId = bundle!!.getString("jobId").toString()
        setHasOptionsMenu(true)
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ApplicationAdapter.OnItemClickCallback{

            override fun onItemClicked(cv: CV) {
                showSelectedCv(cv)
            }
        })

        binding.apply {
            activity?.let {
                rvJob.layoutManager = LinearLayoutManager(it)
            }
            rvJob.adapter = adapter
            viewModel.job.observe(viewLifecycleOwner, {
                adapter.addApplications(it)
            })
            viewModel.getRealtimeUpdate(jobId)


        }



    }

    private fun showSelectedCv(cv: CV) {
        val i = CV(
            cv.applicationid,
            cv.jobId,
            cv.userId,
            cv.username,
            cv.email,
            cv.phoneNumber,
            cv.cv

            )
        activity?.let {
            val intentWithExtraData = Intent(it, ApplicationDetailsActivity::class.java)
            intentWithExtraData.putExtra(ApplicationDetailsActivity.EXTRA_CV, i)
            startActivity(intentWithExtraData)
        }


    }


}