package com.example.energymanagementapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner




class ECarChargingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ecar_charging)

        val dropdown = findViewById<Spinner>(R.id.eCarSpinner)

        val items = arrayOf("Photovoltaic mode", "Fast grid mode", "Smart mode")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)


        dropdown.adapter = adapter
    }
}