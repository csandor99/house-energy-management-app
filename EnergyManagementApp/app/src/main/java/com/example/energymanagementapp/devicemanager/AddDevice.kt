package com.example.energymanagementapp.devicemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import com.example.energymanagementapp.R

class AddDevice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)
    }

    fun save(view: View){
        val nameRef = findViewById<TextView>(R.id.add_name);
        val descriptionRef = findViewById<TextView>(R.id.add_description);
        val locationRef = findViewById<TextView>(R.id.add_location);
        val accessRef = findViewById<Spinner>(R.id.add_access);

        var intent = Intent()
        intent.putExtra("name",nameRef.text.toString())
        intent.putExtra("description",descriptionRef.text.toString())
        intent.putExtra("location",locationRef.text.toString())
        intent.putExtra("access",accessRef.selectedItem.toString())

        setResult(1,intent)
        finish()
    }
}