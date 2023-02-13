package com.pujol.weathermap.data.dto

import com.google.gson.annotations.SerializedName
import com.pujol.weathermap.data.dto.LocalNames

data class LocationCoordinateDto(
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("lat")
    val latitude: Double? = 0.0,
    @SerializedName("local_names")
    val localNames: LocalNames? = LocalNames(),
    @SerializedName("lon")
    val longitude: Double? = 0.0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("state")
    val state: String? = ""
)