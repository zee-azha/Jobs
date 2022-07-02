package com.example.recuirtmentapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recuirtmentapp.databinding.RecylerViewApplicationBinding


import com.example.recuirtmentapp.model.CV


class ApplicationAdapter: RecyclerView.Adapter<ApplicationAdapter.ViewHolder>() {
    private val list= ArrayList<CV>()

    private var onItemClickCallback: OnItemClickCallback? =null



    fun setOnItemClickCallback(onItemClickCallback:OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationAdapter.ViewHolder {
        val binding = RecylerViewApplicationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ApplicationAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun  addApplications(CV: CV){
        if (!list.contains(CV)){
            list.add(CV)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecylerViewApplicationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(CV: CV){
            with(binding){
                tvName.text = CV.username
                tvEmail.text = CV.email
                cvApplication.setOnClickListener{
                    onItemClickCallback?.onItemClicked(CV)
                }
            }
        }

    }
    interface OnItemClickCallback {
        fun onItemClicked(CV : CV)

    }
}