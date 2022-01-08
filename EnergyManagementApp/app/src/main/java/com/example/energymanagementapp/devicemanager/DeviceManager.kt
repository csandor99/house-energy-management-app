package com.example.energymanagementapp.devicemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.adapters.MyAdapter
import com.example.energymanagementapp.models.DeviceList

class DeviceManager : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_manager)
        val listViewReference = findViewById <ListView> ( R.id.list_view)
        myAdapter = MyAdapter ( this, DeviceList.getDevices() )
        listViewReference.adapter = myAdapter
        myAdapter.notifyDataSetChanged()
    }
}