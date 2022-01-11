package com.example.energymanagementapp.manageEfficiency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.energymanagementapp.R

class PanelManagementActivity : AppCompatActivity() {

    var red_bar: SeekBar? = null
    var orange_bar: SeekBar? = null
    var yellow_bar: SeekBar? = null

    var red_text: TextView? = null
    var orange_text: TextView? = null
    var yellow_text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel_management)

        red_bar = findViewById(R.id.red_seekbar);
        orange_bar = findViewById(R.id.orange_seekbar)
        yellow_bar = findViewById(R.id.yellow_seekbar)


        red_text = findViewById(R.id.red_text);
        orange_text = findViewById(R.id.orange_text)
        yellow_text = findViewById(R.id.yellow_text)


        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        red_bar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar

                red_text?.text = "$i%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()

            }

        })

        orange_bar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                orange_text?.text = "$i%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }

        })

        yellow_bar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                yellow_text?.text = "$i%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }

        })


    }
}
