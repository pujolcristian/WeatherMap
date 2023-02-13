package com.pujol.weathermap.data.remote

import com.pujol.weathermap.data.dto.LocationCoordinateDto
import retrofit2.http.GET
import retrofit2.http.Query


interface LocationCoordinateService {

    @GET("/geo/1.0/direct?")
    suspend fun getLocationCoordinate(
        @Query("q") q: String,
        @Query("limit") limit: String,
        @Query("appid") appid: String
    ): List<LocationCoordinateDto>

}