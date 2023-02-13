package com.pujol.weathermap.data.mapper

import com.pujol.weathermap.data.dto.LocationWeatherDto
import com.pujol.weathermap.domain.model.LocationWeather

fun LocationWeatherDto.toLocationWeather(): LocationWeather {
    return LocationWeather(
        clouds = clouds,
        coordinated = coordinated,
        main = main,
        name = name,
        sys = sys,
        dt = dt?.toLong(),
        timezone = timezone,
        weather = weather,
        wind = wind
    )
}