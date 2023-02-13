package com.pujol.weathermap.di

import android.content.Context
import androidx.room.Room
import com.pujol.weathermap.core.util.Constants
import com.pujol.weathermap.data.data_base.LocationCoordinateDao
import com.pujol.weathermap.data.data_base.WeatherMapDataBase
import com.pujol.weathermap.data.remote.LocationCoordinateService
import com.pujol.weathermap.data.remote.LocationTimeService
import com.pujol.weathermap.data.remote.LocationWeatherService
import com.pujol.weathermap.data.repository.LocationCoordinateRepositoryImpl
import com.pujol.weathermap.data.repository.LocationWeatherRepositoryImpl
import com.pujol.weathermap.domain.repository.LocationCoordinateRepository
import com.pujol.weathermap.domain.repository.LocationWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationCoordinateService(): LocationCoordinateService {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationCoordinateService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationWeatherService(): LocationWeatherService {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationWeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationTimeService(): LocationTimeService {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE_TIME)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().build())
            .build()
            .create(LocationTimeService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherMapDataBase(@ApplicationContext appContext: Context): WeatherMapDataBase {
        return Room.databaseBuilder(
            appContext,
            WeatherMapDataBase::class.java,
            "WeatherMapDataBase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationCoordinateDao(weatherMapDataBase: WeatherMapDataBase): LocationCoordinateDao {
        return weatherMapDataBase.locationCoordinateDao()
    }


    @Provides
    @Singleton
    fun provideLocationCoordinateRepository(
        locationCoordinateService: LocationCoordinateService,
        locationCoordinateDao: LocationCoordinateDao
    ): LocationCoordinateRepository {
        return LocationCoordinateRepositoryImpl(locationCoordinateService, locationCoordinateDao)
    }

    @Provides
    @Singleton
    fun provideLocationWeatherRepository(
        locationWeatherService: LocationWeatherService,
        locationTimeService: LocationTimeService
    ): LocationWeatherRepository {
        return LocationWeatherRepositoryImpl(locationWeatherService, locationTimeService)
    }
}