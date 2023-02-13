package com.pujol.weathermap.domain.model

import com.pujol.weathermap.data.dto.*

data class LocationWeather(
    val clouds: Clouds? = Clouds(),
    val coordinated: Coordinated? = Coordinated(),
    val main: Main? = Main(),
    val name: String? = "",
    val dt: Long? = 0,
    val sys: Sys? = Sys(),
    val timezone: Int? = 0,
    val weather: List<Weather?>? = listOf(),
    val wind: Wind? = Wind()
)
