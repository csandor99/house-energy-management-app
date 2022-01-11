package com.example.energymanagementapp.models

class ItemOnOffList {
    companion object{
        fun getItems(): ArrayList<Source>{
            return arrayListOf(
                Source("Bedroom lights"),
                Source("Living room lights"),
                Source("Kitchen lights"),
                Source("Bathroom lights"),
                Source("Kids room lights")
            );
        }
    }
}