package com.example.energymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.models.Source

class EnergySourceAdapter(val context: Context) : RecyclerView.Adapter<EnergySourceAdapter.MyViewHolder>(){
    var sourceList: List<Source> = listOf()
    var position: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.energy_source,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = sourceList.get(position).name
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    fun setEnergySourceList(sourceList: List<Source>)
    {
        this.sourceList = sourceList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: TextView = itemView!!.findViewById(R.id.source_name)

    }
}