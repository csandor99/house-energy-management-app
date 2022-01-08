package com.example.energymanagementapp.models

class SourceList {
    companion object{
        fun getSources(): ArrayList<Source>{
            return arrayListOf(
                Source("Lighting system"),
                Source("Heating system"),
                Source("Electronic devices"),
                Source("Garden devices")
            );
        }
    }
}