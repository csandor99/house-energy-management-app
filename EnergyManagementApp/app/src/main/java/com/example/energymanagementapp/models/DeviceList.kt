package com.example.energymanagementapp.models

class DeviceList {
    companion object {
        fun getDevices(): ArrayList<Device> {
            return arrayListOf(
                Device("TV" ,"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and","Living room", "all", true),
                Device("Bluetooth speaker","Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto ", "Bedroom", "all", true),
                Device("Watering system", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings ", "Garden", "admin",true),
                Device("Lamp 1", "Voluptatem accusantium doloremque laudantium But I must explain denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings ", "Bedroom", "all",true)
            )
        } }
}