package com.example.energymanagementapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.adapters.PanelCustomAdapter
import com.example.energymanagementapp.models.PanelViewModel
import com.google.android.material.slider.Slider

class SlopeAdjustmentActivity : AppCompatActivity() {

    private val data = ArrayList<PanelViewModel>()
    private lateinit var  adapter:PanelCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slope_adjustment)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerPanelView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(PanelViewModel( "Panel " + i,0f,false,false))
        }

        // This will pass the ArrayList to our Adapter
        adapter = PanelCustomAdapter(data)
        // Setting the Adapter with the recyclerview


        adapter.listener= {x -> showDialog("Open Dialog",x)

        };
        recyclerview.adapter = adapter



    }

    private fun showDialog(title: String,position: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.panel_dialog_view)
        //val body = dialog.findViewById(R.id.body) as TextView
        //body.text = title
        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
        val slopeSlider = dialog.findViewById(R.id.slopeSlider) as Slider
        val toggleButton = dialog.findViewById(R.id.toggleButton) as ToggleButton

        slopeSlider.value=data[position].slope;
        toggleButton.isChecked = data[position].auto;

        slopeSlider.isEnabled = !toggleButton.isChecked

        toggleButton.setOnClickListener{
            slopeSlider.isEnabled = !toggleButton.isChecked
        }
        yesBtn.setOnClickListener {

            data[position].slope = slopeSlider.value
            data[position].auto= toggleButton.isChecked
            adapter.notifyItemChanged(position)
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}