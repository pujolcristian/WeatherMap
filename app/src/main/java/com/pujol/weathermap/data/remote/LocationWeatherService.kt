package com.pujol.weathermap.data.remote

import com.pujol.weathermap.data.dto.LocationWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationWeatherService {

    @GET("data/2.5/weather?")
    suspend fun getLocationWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ): LocationWeatherDto
}