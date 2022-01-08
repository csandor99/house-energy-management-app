package com.example.energymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.models.Source

class ItemOnOffAdapter (val context: Context): RecyclerView.Adapter<ItemOnOffAdapter.MyViewHolder>(){
    var itemList: List<Source> = listOf()
    var position: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOnOffAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onoff,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemOnOffAdapter.MyViewHolder, position: Int) {
        holder.name.text = itemList.get(position).name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemsList(sourceList: List<Source>)
    {
        this.itemList = sourceList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: TextView = itemView!!.findViewById(R.id.item_name)

    }

}