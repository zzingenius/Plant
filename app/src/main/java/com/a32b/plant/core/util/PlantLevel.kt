package com.a32b.plant.core.util

import com.a32b.plant.R

enum class PlantLevel(val label: String, val imgRes: Int){
    LV0("lv.0", R.drawable.ic_pot_lv0),
    LV1("lv.1",R.drawable.ic_pot_lv1),
    LV2("lv.2",R.drawable.ic_pot_lv2),
    LV3("lv.3",R.drawable.ic_pot_lv3),
    LV4("lv.4",R.drawable.ic_pot_lv4),
    LV5("lv.5",R.drawable.ic_pot_lv5),
    LV6("lv.6",R.drawable.ic_pot_lv6),
    DEFAULT("default", R.drawable.logo_plant);

    companion object{
        fun getPlantImage(level: String?): Int{
           return entries.find { it.label == level }?.imgRes ?: DEFAULT.imgRes
        }
    }
}