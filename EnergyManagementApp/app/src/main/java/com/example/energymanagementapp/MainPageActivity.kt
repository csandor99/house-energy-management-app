package com.example.energymanagementapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.navigation.NavigationView
import android.widget.CalendarView.OnDateChangeListener
import com.example.energymanagementapp.energySource.EnergySourceActivity
import com.example.energymanagementapp.manageEfficiency.PanelManagementActivity
import com.example.energymanagementapp.turnOnOff.TurnOnOffActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainPageActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var pieChart:PieChart
    lateinit var barChart: HorizontalBarChart

    var scoresList: ArrayList<Score> = ArrayList<Score>()
    var scoresList2: ArrayList<Score> = ArrayList<Score>()

    var calendar:ImageView? = null
    var calendarView: CalendarView? = null
    var date: TextView? = null
    var searchText: EditText? = null
    var searchButton: FloatingActionButton? = null
    var title: TextView? = null
    var check:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        pieChart = findViewById(R.id.pieChart)
        barChart = findViewById(R.id.barChart)
        calendar = findViewById(R.id.calendar)
        calendarView = findViewById(R.id.calendarViewMainPage)
        calendarView?.visibility = View.INVISIBLE
        searchText = findViewById(R.id.searchText)
        searchText?.visibility = View.INVISIBLE
        searchButton = findViewById(R.id.searchButton)
        searchButton?.visibility = View.INVISIBLE
        title = findViewById(R.id.titlu)

        date = findViewById(R.id.date)

        initPieChart()
        setDataToPieChart()

        scoresList = getScoreList()
        initBarChart()

        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in scoresList.indices) {
            val score = scoresList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()


        toggle =  ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_battery -> Toast.makeText(applicationContext, "Clicked battery", Toast.LENGTH_SHORT).show()
                R.id.nav_devices -> Toast.makeText(applicationContext, "Clicked devices", Toast.LENGTH_SHORT).show()
                R.id.nav_consumption -> Toast.makeText(applicationContext, "Clicked consumption", Toast.LENGTH_SHORT).show()
                R.id.nav_slope -> Toast.makeText(applicationContext, "Clicked slope", Toast.LENGTH_SHORT).show()
                R.id.nav_efficiency -> {
                    val intent = Intent ( this, PanelManagementActivity::class.java )
                    startActivity ( intent )
                //Toast.makeText(applicationContext, "Clicked efficiency", Toast.LENGTH_SHORT).show()}
                }
                R.id.nav_provider -> {
                    val intent = Intent ( this, EnergySourceActivity::class.java )
                    startActivity ( intent )
                //Toast.makeText(applicationContext, "Clicked provider", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_ecar -> Toast.makeText(applicationContext, "Clicked ecar", Toast.LENGTH_SHORT).show()
                R.id.logout -> logout()
            }
            true
        }


        calendar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                calendarView?.visibility = View.VISIBLE
                pieChart?.visibility = View.INVISIBLE
                barChart?.visibility = View.INVISIBLE

                calendarView?.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
                    var m = month + 1


                    var calendar = Calendar.getInstance();
                    var dateFormat = SimpleDateFormat("dd/MM/yyyy");
                    var today = dateFormat.format(calendar.getTime());

                    var d = ""
                    if(1<= m && m <=9)
                    {
                        d = dayOfMonth.toString() + "/0" + m.toString() + "/" + year.toString()
                    }else
                    {
                        d = dayOfMonth.toString() + "/" + m.toString() + "/" + year.toString()
                    }


                    date?.text = d + "(kw/day)"
                    calendarView?.visibility = View.INVISIBLE

                    pieChart?.visibility = View.VISIBLE
                    barChart?.visibility = View.VISIBLE


                    barChart.data?.clearValues()
                    barChart.notifyDataSetChanged()
                    barChart.clear()
                    barChart.invalidate()
                    initBarChart()


                    if(today.compareTo(d, false) < 0)
                    {
                        scoresList2 = ArrayList<Score>()

                    }else
                    {
                        scoresList2 = getScoreList2()
                        val entries2: ArrayList<BarEntry> = ArrayList()

                        for (i in scoresList2.indices) {
                            val score = scoresList2[i]
                            entries2.add(BarEntry(i.toFloat(), score.score.toFloat()))
                        }

                        val barDataSet = BarDataSet(entries2, "")
                        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

                        val data = BarData(barDataSet)
                        barChart.data = data

                        barChart.invalidate()

                    }

                })
            }

        })

        searchButton?.setOnClickListener({
            val intent = Intent ( this, TurnOnOffActivity::class.java )
            startActivity ( intent )
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.search ->{
                if(!check)
                {
                    searchText?.visibility = View.VISIBLE
                    searchButton?.visibility = View.VISIBLE
                    title?.visibility = View.INVISIBLE
                    check = true

                }else
                {
                    searchText?.visibility = View.INVISIBLE
                    searchButton?.visibility = View.INVISIBLE
                    title?.visibility = View.VISIBLE
                    check = false
                }

            }
        }



        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        logout()
    }

    private fun logout(){
        val builder = AlertDialog.Builder ( this )
        builder.setTitle ( "Confirmation" )
            .setMessage ( "Do you really want to log out?" )
            .setPositiveButton ( "Confirm", DialogInterface.OnClickListener(confirmListener) )
            .setNegativeButton ( "Cancel", DialogInterface.OnClickListener(cancelListener) )
        builder.create().show()
    }

    private val confirmListener = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,"Logged out",Toast.LENGTH_SHORT).show()
        val i = Intent(this, MainActivity::class.java);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        this.finish();
    }

    private val cancelListener = {dialog: DialogInterface, which: Int ->

    }

    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true

    }

    private fun setDataToPieChart() {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(20f, "Exported"))
        dataEntries.add(PieEntry(37f, "Boiler"))
        dataEntries.add(PieEntry(15f, "Car"))
        dataEntries.add(PieEntry(28f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#62BD69"))
        colors.add(Color.parseColor("#FF8A65"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Total consumption this month (%)"



        pieChart.invalidate()

    }

    private fun initBarChart() {


//        hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false


        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = -90f//+90f

    }

    data class Score(
        val name:String,
        val score: Double,
    )

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            //Log.d(TAG, "getAxisLabel: index $index")
            return if (index < scoresList.size) {
                scoresList[index].name
            } else {
                ""
            }
        }
    }


    // simulate api call
    // we are initialising it directly
    private fun getScoreList(): ArrayList<Score> {
        scoresList.add(Score("Exported", 10.0))
        scoresList.add(Score("Boiler", 15.15))
        scoresList.add(Score("Car", 7.87))
        scoresList.add(Score("Other", 18.23))

        return scoresList
    }

    private fun getScoreList2(): ArrayList<Score> {
        scoresList2.add(Score("Exported", 9.0))
        scoresList2.add(Score("Boiler", 16.15))
        scoresList2.add(Score("Car", 5.3))
        scoresList2.add(Score("Other", 20.7))

        return scoresList2
    }


}