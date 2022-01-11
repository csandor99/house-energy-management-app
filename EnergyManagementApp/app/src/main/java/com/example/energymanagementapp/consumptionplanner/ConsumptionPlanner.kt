package com.example.energymanagementapp.consumptionplanner

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.energymanagementapp.R
import com.example.energymanagementapp.adapters.WeatherRVAdapter
import com.example.energymanagementapp.models.WeatherRVModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.card.MaterialCardView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener


class ConsumptionPlanner : AppCompatActivity() {

    private lateinit var weatherRV: RecyclerView
    private lateinit var weatherRVModalArrayList: ArrayList<WeatherRVModel>
    private lateinit var weatherRVAdapter: WeatherRVAdapter
    private lateinit var locationManager: LocationManager
    var PERMISSION_CODE: Int = 1
    var cityName:String = "Cluj-Napoca"

    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption_planner)

        queue = Volley.newRequestQueue(this)

        weatherRV = findViewById(R.id.idRvWeather)
        weatherRVModalArrayList = ArrayList()
        weatherRVAdapter = WeatherRVAdapter(weatherRVModalArrayList)
        weatherRV.adapter = weatherRVAdapter

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_CODE)
        }

        var location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            cityName = getCityName(location.longitude,location.latitude)
        }

        getWeatherInfo(cityName)

        val findModeTV = findViewById<TextView>(R.id.find_more)
        findModeTV.movementMethod = LinkMovementMethod.getInstance()


        val calendar1Btn = findViewById<ImageView>(R.id.calendarpick)
        val calendar2Btn = findViewById<ImageView>(R.id.calendarpickdevice)
        val calendar = findViewById<MaterialCalendarView>(R.id.calendarViewSolarPanel)
        calendar.state().edit().setMinimumDate(CalendarDay.today()).commit()
        val calendar2 = findViewById<MaterialCalendarView>(R.id.calendarViewDevice)
        calendar2.state().edit().setMinimumDate(CalendarDay.today()).commit()
        val display = findViewById<LinearLayout>(R.id.consumption_display)

        val fromTV = findViewById<TextView>(R.id.from_field)
        val toTV = findViewById<TextView>(R.id.to_field)
        val deviceDateTV = findViewById<TextView>(R.id.device_date)

        calendar1Btn.setOnClickListener {
            calendar.visibility = View.VISIBLE
            display.visibility = View.GONE
            calendar.setOnRangeSelectedListener(
                OnRangeSelectedListener(fun(view,dates){

                    calendar.visibility = View.GONE
                    display.visibility = View.VISIBLE
                    val calendarDays = dates as ArrayList<CalendarDay>
                    val month1 = calendarDays[0].month + 1
                    val month2 = calendarDays[calendarDays.size-1].month + 1
                    fromTV.text = calendarDays[0].day.toString() + "/"+ month1.toString() +"/"+ calendarDays[0].year.toString()
                    toTV.text = calendarDays[calendarDays.size-1].day.toString() + "/"+ month2.toString() +"/"+ calendarDays[calendarDays.size-1].year.toString()
                })
            )
        }

        calendar2Btn.setOnClickListener {
            calendar2.visibility = View.VISIBLE
            display.visibility = View.GONE
            calendar2.setOnDateChangedListener(
                OnDateSelectedListener(fun(view,dates,unit){

                    calendar2.visibility = View.GONE
                    display.visibility = View.VISIBLE
                    val month = dates.month + 1
                    deviceDateTV.text = dates.day.toString() + "/"+ month.toString() +"/"+ dates.year.toString()
                })
            )
        }

        val button = findViewById<Button>(R.id.use_plan)
        val devFromTime = findViewById<Spinner>(R.id.device_from_time)
        val devToTime = findViewById<Spinner>(R.id.device_to_time)
        val deviceSpinner = findViewById<Spinner>(R.id.device_spinner)

        button.setOnClickListener {
            if(deviceDateTV.text != "Select date"){
                if(checkHours(devFromTime.selectedItem.toString(),devToTime.selectedItem.toString())){
                    Toast.makeText(this, "Plan set for " + deviceSpinner.selectedItem.toString()
                            + " on date " + deviceDateTV.text
                            + " between " + devFromTime.selectedItem.toString()
                            + " and " + devToTime.selectedItem.toString(),Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Please select a date for device plan!",Toast.LENGTH_LONG).show()
            }
        }

        val solarSwitch = findViewById<SwitchCompat>(R.id.solar_panels_switch)

        solarSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(fromTV.text == "From date"){
                    solarSwitch.isChecked = false
                    Toast.makeText(this, "Please select a date for solar panel plan!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Solar panel plan set between "
                            + fromTV.text
                            + " and " + toTV.text,Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Solar panel plan disabled!",Toast.LENGTH_LONG).show()
            }

        }

    }

    fun checkHours(h1: String, h2: String):Boolean{
        val hour1 = h1.split(":")
        val hour2 = h2.split(":")

        if(hour1[0].toInt() >= hour2[0].toInt()){
            Toast.makeText(this, "The hour range is invalid!",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_CODE){
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted... ",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please provide the permission",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun getCityName(longitude: Double, latitude: Double): String{
        var cityName = "Not found"
        var gcd: Geocoder = Geocoder(baseContext, Locale.getDefault())
        try {
            var addresses:List<Address> = gcd.getFromLocation(latitude,longitude,10)

            for (adr in addresses){
                if(adr?.locality != null){
                    var city: String = adr.locality
                    if(city!=null && city != ""){
                        cityName=city
                    }else{
                        Log.d("TAG", "CITY NOT FOUND")
                        Toast.makeText(this,"User City Not Found...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:IOException){
            e.printStackTrace()
        }

        return cityName

    }

    @SuppressLint("NotifyDataSetChanged")
    fun getWeatherInfo(cityName: String){
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=a78e43d55bca4477bfd151916220901&q=$cityName&days=1&aqi=yes&alerts=yes"


        var jsonObjectRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.d("AAAAAAAAAAAA","call successfull")
                try{
                    weatherRVModalArrayList.clear()
                    val obj: JSONObject = JSONObject(response)
                    val forecast: JSONObject = obj.getJSONObject("forecast")
                    val forecast0: JSONObject = forecast.getJSONArray("forecastday").getJSONObject(0)
                    val hourArray: JSONArray = forecast0.getJSONArray("hour")

                    for(i in 0 until hourArray.length()){
                        val hourObj: JSONObject = hourArray.getJSONObject(i)

                        val time: String = hourObj.getString("time")
                        val temperature: String = hourObj.getString("temp_c")
                        val img: String = hourObj.getJSONObject("condition").getString("icon")
                        val wind: String = hourObj.getString("wind_kph")
                        Log.d("stats",time+temperature+img+wind+"\n\n\n\n")
                        weatherRVModalArrayList.add(WeatherRVModel(time,temperature,img,wind))
                    }

                    weatherRVAdapter.notifyDataSetChanged()
                }catch (e: JSONException){
                e.printStackTrace()
                }

            },
            {
                Log.d("Abbbbbbbbb","call unsuccessfull")
            }
        )
        queue.add(jsonObjectRequest)
//        VolleySingleton.getInstance(applicationContext)
//            .addToRequestQueue(jsonObjectRequest)
    }
}