package com.pujol.weathermap.data.dto


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int? = 0
)