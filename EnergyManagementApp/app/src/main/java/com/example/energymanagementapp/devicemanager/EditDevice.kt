package com.example.energymanagementapp.devicemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.energymanagementapp.R

class EditDevice : AppCompatActivity() {
    var position = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_device)
        val name = intent.getStringExtra ( "name" )
        val description = intent.getStringExtra ( "description" )
        val location = intent.getStringExtra ( "location" )
        val access = intent.getStringExtra ( "access" )
        val connected = intent.getBooleanExtra("connected",false)
        position = intent.getIntExtra("position",0)

        val nameRef = findViewById<TextView>(R.id.edit_name);
        val descriptionRef = findViewById<TextView>(R.id.edit_description);
        val locationRef = findViewById<TextView>(R.id.edit_location);
        val accessRef = findViewById<Spinner>(R.id.edit_access);
        val connectedSwtichRef = findViewById<SwitchCompat>(R.id.connected_switch)

        nameRef.text = name
        descriptionRef.text = description
        locationRef.text = location
        val adapter: ArrayAdapter<String>  = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            getResources().getStringArray(R.array.category_array));
        val spinnerPosition: Int = adapter.getPosition(access)
        accessRef.setSelection(spinnerPosition)

        connectedSwtichRef.isChecked = connected
    }

    fun edit(view: View){
        val nameRef = findViewById<TextView>(R.id.edit_name);
        val descriptionRef = findViewById<TextView>(R.id.edit_description);
        val locationRef = findViewById<TextView>(R.id.edit_location);
        val accessRef = findViewById<Spinner>(R.id.edit_access);
        val connectedSwitchRef = findViewById<SwitchCompat>(R.id.connected_switch)

        var intent = Intent()
        intent.putExtra("name",nameRef.text.toString())
        intent.putExtra("description",descriptionRef.text.toString())
        intent.putExtra("location",locationRef.text.toString())
        intent.putExtra("access",accessRef.selectedItem.toString())
        if(connectedSwitchRef.isChecked){
            intent.putExtra("connected",true)
        }else{
            intent.putExtra("connected",false)
        }
        intent.putExtra("position",position)

        setResult(0,intent)
        finish()
    }
}