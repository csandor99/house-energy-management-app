package com.example.energymanagementapp.models

import java.util.*
import kotlin.collections.ArrayList

class BatteryPowerDataList() {

    var data = ArrayList<BatteryPowerData>()

    init {
        data.add(BatteryPowerData(Date(2022-1900, 0, 1, 5, 0), 75))
        data.add(BatteryPowerData(Date(2022-1900, 0, 4, 5, 0), 75))
        data.add(BatteryPowerData(Date(2022-1900, 0, 5, 5, 0), 75))
        data.add(BatteryPowerData(Date(2022-1900, 0, 29, 1, 0), 56))
        data.add(BatteryPowerData(Date(2022-1900, 0, 30, 2, 0), 75))
        data.add(BatteryPowerData(Date(2022-1900, 1, 4, 3, 0), 85))
        data.add(BatteryPowerData(Date(2022-1900, 1, 4, 4, 0), 45))
        data.add(BatteryPowerData(Date(2022-1900, 1, 4, 5, 0), 63))
    }


}