package com.example.energymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.models.Device

class MyAdapter (private val context: Context,
private var dataSource: ArrayList<Device>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Device {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long{
        return Long.MIN_VALUE
    }

    fun modifyDevice(device: Device,position: Int){
        dataSource[position] = device
    }

    fun addDevice(device: Device){
        dataSource.add(device)
    }

    override fun getView (position: Int,
                          containerView: View?,
                          viewGroupParent: ViewGroup?) : View
    {
        // reuse OR expand the view for one entry into the list
        val rowView = containerView ?: inflater.inflate(
            R.layout.device_list_element,
            viewGroupParent, false)
        // get the visual elements and update them with the information from the model
        // for example:
        // val textView = findViewById <TextView> ( R.id.idTitleTextView )
        // textView.text = getItem(position).title

        val textNameView = rowView.findViewById<TextView>(R.id.device_name)
        textNameView.text=getItem(position).name

        val textStatusLocView= rowView.findViewById<TextView>(R.id.device_stat_loc)
        val msg = if (getItem(position).connected) "Online" else "Offline"
        textStatusLocView.text= msg + " - " + getItem(position).location

        val connectedImgView = rowView.findViewById<ImageView>(R.id.connected_img)
        if (getItem(position).connected) connectedImgView.setImageResource(R.drawable.ic_baseline_check_circle_24) else connectedImgView.setImageResource(R.drawable.ic_baseline_cancel_24)

        return rowView
    }
}