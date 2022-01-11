package com.example.energymanagementapp.devicemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.adapters.MyAdapter
import com.example.energymanagementapp.models.Device
import com.example.energymanagementapp.models.DeviceList
import java.util.*

class DeviceManager : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_manager)
        val listViewReference = findViewById <ListView> ( R.id.list_view)
        myAdapter = MyAdapter ( this, DeviceList.getDevices() )
        listViewReference.adapter = myAdapter
        myAdapter.notifyDataSetChanged()

        listViewReference.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, EditDevice::class.java)
            intent.putExtra("name", myAdapter.getItem(position).name)
            intent.putExtra("description", myAdapter.getItem(position).description)
            intent.putExtra("location", myAdapter.getItem(position).location)
            intent.putExtra("access", myAdapter.getItem(position).access)
            intent.putExtra("connected", myAdapter.getItem(position).connected)
            intent.putExtra("position",position);
            startActivityForResult(intent,0)
        }

        val addButton = findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddDevice::class.java)
            startActivityForResult(intent,1);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(data!=null){
                var editedDevice = myAdapter.getItem(data!!.getIntExtra("position",0))
                    //Device("","","","",false)
                editedDevice.name = data!!.getStringExtra("name").toString()
                editedDevice.description = data!!.getStringExtra("description").toString()
                editedDevice.location = data!!.getStringExtra("location").toString()
                editedDevice.access = data!!.getStringExtra("access").toString()
                editedDevice.connected = data!!.getBooleanExtra("connected",false)


                myAdapter.modifyDevice(editedDevice,data!!.getIntExtra("position",0))
                myAdapter.notifyDataSetChanged()

            }

        }

        if(requestCode == 1){
            if(data!=null){
                var newDevice = Device("","","","",true)

                newDevice.name = data!!.getStringExtra("name").toString()
                newDevice.description = data!!.getStringExtra("description").toString()
                newDevice.location = data!!.getStringExtra("location").toString()
                newDevice.access = data!!.getStringExtra("access").toString()


                myAdapter.addDevice(newDevice)
                myAdapter.notifyDataSetChanged()

            }

        }
    }
}