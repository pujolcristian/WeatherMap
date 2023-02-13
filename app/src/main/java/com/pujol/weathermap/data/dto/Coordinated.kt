package com.pujol.weathermap.data.dto


import com.google.gson.annotations.SerializedName

data class Coordinated(
    @SerializedName("lat")
    val latitude: Double? = 0.0,
    @SerializedName("lon")
    val longitude: Double? = 0.0
)