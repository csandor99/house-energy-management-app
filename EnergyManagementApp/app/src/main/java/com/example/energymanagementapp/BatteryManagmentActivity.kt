package com.example.energymanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BatteryManagmentActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var materialCalendar: MaterialCalendarView
    data class Score(
        val date:Date,
        val value: Int,
    )
    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_managment)
        lineChart = findViewById(R.id.chart)

        materialCalendar = findViewById<MaterialCalendarView>(R.id.calendarView)


        println( "set event")
        materialCalendar.setOnRangeSelectedListener(
            OnRangeSelectedListener(fun(view,dates){
            println(dates)
        }))
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
            return if (index < scoreList.size) {
                val sdf = SimpleDateFormat("dd MMM hh")
                return sdf.format( scoreList[index].date);
              // .toString()
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        scoreList = getScoreList()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.value.toFloat()))
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
    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score(Date(2022,1,4,1,0), 56))
        scoreList.add(Score(Date(2022,1,4,2,0), 75))
        scoreList.add(Score(Date(2022,1,4,3,0), 85))
        scoreList.add(Score(Date(2022,1,4,4,0), 45))
        scoreList.add(Score(Date(2022,1,4,5,0), 63))

        return scoreList
    }



}