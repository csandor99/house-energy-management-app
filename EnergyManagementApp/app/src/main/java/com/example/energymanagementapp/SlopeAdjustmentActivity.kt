package com.example.energymanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.adapters.PanelCustomAdapter
import com.example.energymanagementapp.models.PanelViewModel

class SlopeAdjustmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slope_adjustment)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerPanelView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<PanelViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(PanelViewModel( "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = PanelCustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}