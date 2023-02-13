package com.pujol.weathermap.data.dto


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("sunrise")
    val sunrise: Long? = 0,
    @SerializedName("sunset")
    val sunset: Long? = 0,
    @SerializedName("type")
    val type: Int? = 0
)