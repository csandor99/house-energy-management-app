package com.example.energymanagementapp.turnOnOff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.adapters.EnergySourceAdapter
import com.example.energymanagementapp.adapters.ItemOnOffAdapter
import com.example.energymanagementapp.models.ItemOnOffList

class TurnOnOffActivity : AppCompatActivity() {

    lateinit var itemOnOffView: RecyclerView
    lateinit var itemOnOffAdapter: ItemOnOffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn_on_off)

        itemOnOffView = findViewById(R.id.itemOnOffRecyclerView)
        itemOnOffAdapter = ItemOnOffAdapter(this)
        itemOnOffView.layoutManager = LinearLayoutManager(this)
        itemOnOffView.adapter = itemOnOffAdapter

        itemOnOffAdapter.setItemsList(ItemOnOffList.getItems())
    }
}