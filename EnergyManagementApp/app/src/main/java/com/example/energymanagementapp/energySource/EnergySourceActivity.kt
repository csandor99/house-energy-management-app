package com.example.energymanagementapp.energySource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.adapters.EnergySourceAdapter
import com.example.energymanagementapp.models.SourceList

class EnergySourceActivity : AppCompatActivity() {

    lateinit var energySourceView: RecyclerView
    lateinit var energySourceAdapter: EnergySourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_energy_source)

        energySourceView = findViewById(R.id.energySourceRecyclerView)
        energySourceAdapter = EnergySourceAdapter(this)
        energySourceView.layoutManager = LinearLayoutManager(this)
        energySourceView.adapter = energySourceAdapter

        energySourceAdapter.setEnergySourceList(SourceList.getSources())
    }
}