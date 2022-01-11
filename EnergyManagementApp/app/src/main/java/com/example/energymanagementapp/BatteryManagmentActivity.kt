package com.example.energymanagementapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.energymanagementapp.models.BatteryPowerDataList
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BatteryManagmentActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var materialCalendar: MaterialCalendarView
    private lateinit var selectButton:Button

    private var calendarDays=ArrayList<CalendarDay>()

    private var batteryPowerList = BatteryPowerDataList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_managment)
        lineChart = findViewById(R.id.chart)
        selectButton = findViewById(R.id.selectDateButton)

        materialCalendar = findViewById<MaterialCalendarView>(R.id.calendarView)


        println( "set event")
        materialCalendar.setOnRangeSelectedListener(
            OnRangeSelectedListener(fun(view,dates){

                materialCalendar.visibility = View.GONE
                selectButton.visibility=View.VISIBLE
                calendarDays = dates as ArrayList<CalendarDay>
                setDataToLineChart()
                lineChart.notifyDataSetChanged()
                lineChart.invalidate()
                println(dates)
        }))

        selectButton.setOnClickListener {
            materialCalendar.visibility = View.VISIBLE
            selectButton.visibility=View.GONE
        }
        initLineChart()


        setDataToLineChart()
    }



    private fun initLineChart() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false


        //remove description label
        lineChart.description.isEnabled = false


        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < batteryPowerList.data.size) {
                val sdf = SimpleDateFormat("dd MMM hh")
                return sdf.format( batteryPowerList.data[index].date);
              // .toString()
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

       // batteryPowerList = getBatteryPowerList()

        //you can replace this data object with  your custom object
        // check if in time interval
        println("set data charts !!!")
        if(calendarDays.size == 0){
            for (i in batteryPowerList.data.indices) {
                val score = batteryPowerList.data[i]
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
            }
        }else {
            for (i in batteryPowerList.data.indices) {
                val score = batteryPowerList.data[i]
                println(calendarDays[0].date )

                println(score.date  )
                println(calendarDays.last().date)

                println("first compare")
                println(calendarDays[0].date.before(score.date ))
                println("last compare")
                println( score.date.before(calendarDays.last().date))
                if(calendarDays[0].date.before(score.date )&&
                    score.date.before(calendarDays.last().date) ) {
                    entries.add(Entry(i.toFloat(), score.value.toFloat()))
                }
            }
        }

        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setDrawFilled(true)
        val data = LineData(lineDataSet)
        //data.set
        lineChart.data = data

        lineChart.invalidate()
    }

    // simulate api call
    // we are initialising it directly




}