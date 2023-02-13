package com.pujol.weathermap.data.dto


import com.google.gson.annotations.SerializedName

data class LocationTimeDto(
    @SerializedName("abbreviation")
    val abbreviation: String? = "",
    @SerializedName("cityName")
    val cityName: String? = "",
    @SerializedName("countryCode")
    val countryCode: String? = "",
    @SerializedName("countryName")
    val countryName: String? = "",
    @SerializedName("dst")
    val dst: String? = "",
    @SerializedName("formatted")
    val formatted: String? = "",
    @SerializedName("gmtOffset")
    val gmtOffset: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("nextAbbreviation")
    val nextAbbreviation: String? = "",
    @SerializedName("regionName")
    val regionName: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("timestamp")
    val timestamp: Long? = 0L,
    @SerializedName("zoneEnd")
    val zoneEnd: Int? = 0,
    @SerializedName("zoneName")
    val zoneName: String? = "",
    @SerializedName("zoneStart")
    val zoneStart: Int? = 0
)