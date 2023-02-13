package com.pujol.weathermap.data.remote

import com.pujol.weathermap.data.dto.LocationTimeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationTimeService {

    @GET("get-time-zone?")
    suspend fun getLocationTime(
        @Query("key") key: String,
        @Query("format") format: String,
        @Query("by") by: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
    ): LocationTimeDto
}