package com.example.energymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.consumptionplanner.ConsumptionPlanner
import com.example.energymanagementapp.models.WeatherRVModel
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.Bitmap

import android.graphics.BitmapFactory

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class WeatherRVAdapter(private val weatherRVModelArrayList: ArrayList<WeatherRVModel>): RecyclerView.Adapter<WeatherRVAdapter.ViewHolder>(){
    val map = mapOf(
        "day/113.png" to R.drawable.day113,
        "day/116.png" to R.drawable.day116,
        "day/119.png" to R.drawable.day119,
        "day/122.png" to R.drawable.day122,
        "day/143.png" to R.drawable.day143,
        "day/176.png" to R.drawable.day176,
        "day/179.png" to R.drawable.day179,
        "day/182.png" to R.drawable.day182,
        "day/185.png" to R.drawable.day185,
        "day/200.png" to R.drawable.day200,
        "day/227.png" to R.drawable.day227,
        "day/230.png" to R.drawable.day230,
        "day/248.png" to R.drawable.day248,
        "day/260.png" to R.drawable.day260,
        "day/263.png" to R.drawable.day263,
        "day/266.png" to R.drawable.day266,
        "day/281.png" to R.drawable.day281,
        "day/284.png" to R.drawable.day284,
        "day/293.png" to R.drawable.day293,
        "day/296.png" to R.drawable.day296,
        "day/299.png" to R.drawable.day299,
        "day/302.png" to R.drawable.day302,
        "day/305.png" to R.drawable.day305,
        "day/308.png" to R.drawable.day308,
        "day/311.png" to R.drawable.day311,
        "day/314.png" to R.drawable.day314,
        "day/317.png" to R.drawable.day317,
        "day/320.png" to R.drawable.day320,
        "day/323.png" to R.drawable.day323,
        "day/326.png" to R.drawable.day326,
        "day/329.png" to R.drawable.day329,
        "day/332.png" to R.drawable.day332,
        "day/335.png" to R.drawable.day335,
        "day/338.png" to R.drawable.day338,
        "day/350.png" to R.drawable.day350,
        "day/353.png" to R.drawable.day353,
        "day/356.png" to R.drawable.day356,
        "day/359.png" to R.drawable.day359,
        "day/362.png" to R.drawable.day362,
        "day/365.png" to R.drawable.day365,
        "day/368.png" to R.drawable.day368,
        "day/371.png" to R.drawable.day371,
        "day/374.png" to R.drawable.day374,
        "day/377.png" to R.drawable.day377,
        "day/386.png" to R.drawable.day386,
        "day/389.png" to R.drawable.day389,
        "day/392.png" to R.drawable.day392,
        "day/395.png" to R.drawable.day395,
        "night/113.png" to R.drawable.night113,
        "night/116.png" to R.drawable.night116,
        "night/119.png" to R.drawable.night119,
        "night/122.png" to R.drawable.night122,
        "night/143.png" to R.drawable.night143,
        "night/176.png" to R.drawable.night176,
        "night/179.png" to R.drawable.night179,
        "night/182.png" to R.drawable.night182,
        "night/185.png" to R.drawable.night185,
        "night/200.png" to R.drawable.night200,
        "night/227.png" to R.drawable.night227,
        "night/230.png" to R.drawable.night230,
        "night/248.png" to R.drawable.night248,
        "night/260.png" to R.drawable.night260,
        "night/263.png" to R.drawable.night263,
        "night/266.png" to R.drawable.night266,
        "night/281.png" to R.drawable.night281,
        "night/284.png" to R.drawable.night284,
        "night/293.png" to R.drawable.night293,
        "night/296.png" to R.drawable.night296,
        "night/299.png" to R.drawable.night299,
        "night/302.png" to R.drawable.night302,
        "night/305.png" to R.drawable.night305,
        "night/308.png" to R.drawable.night308,
        "night/311.png" to R.drawable.night311,
        "night/314.png" to R.drawable.night314,
        "night/317.png" to R.drawable.night317,
        "night/320.png" to R.drawable.night320,
        "night/323.png" to R.drawable.night323,
        "night/326.png" to R.drawable.night326,
        "night/329.png" to R.drawable.night329,
        "night/332.png" to R.drawable.night332,
        "night/335.png" to R.drawable.night335,
        "night/338.png" to R.drawable.night338,
        "night/350.png" to R.drawable.night350,
        "night/353.png" to R.drawable.night353,
        "night/356.png" to R.drawable.night356,
        "night/359.png" to R.drawable.night359,
        "night/362.png" to R.drawable.night362,
        "night/365.png" to R.drawable.night365,
        "night/368.png" to R.drawable.night368,
        "night/371.png" to R.drawable.night371,
        "night/374.png" to R.drawable.night374,
        "night/377.png" to R.drawable.night377,
        "night/386.png" to R.drawable.night386,
        "night/389.png" to R.drawable.night389,
        "night/392.png" to R.drawable.night392,
        "night/395.png" to R.drawable.night395,
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: WeatherRVAdapter.ViewHolder, position: Int) {
        val model: WeatherRVModel = weatherRVModelArrayList[position]

        holder.windTV.text = model.windSpeed + "Km/h"
        holder.temperatureTV.text = model.temperature + "°C"

//        val imgUri = "http:"+model.icon
//        Picasso.get().load(imgUri).into(holder.conditionIV)

        if(model.icon.isNotEmpty()) {
            val parts = model.icon.split("/")
            val str = parts[parts.size-2
            ]+"/"+parts[parts.size-1]
            map[str]?.let {
                Picasso.get()
                    //.load(R.drawable.weatherovercast)
                    .load(it)
                    .into(holder.conditionIV)
            };
        }

        val input = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val output = SimpleDateFormat("hh:mm aa")

        try{
            val t: Date = input.parse(model.time)
            holder.timeTV.text = output.format(t)
        }catch (e: ParseException){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return weatherRVModelArrayList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var windTV: TextView
        var temperatureTV: TextView
        var timeTV: TextView
        var conditionIV: ImageView

        init {
            windTV = itemView.findViewById(R.id.idTVWindSpeed)
            temperatureTV = itemView.findViewById(R.id.idTVTemperature)
            timeTV = itemView.findViewById(R.id.idTVTime)
            conditionIV = itemView.findViewById(R.id.idIVCondition)
        }
    }
}