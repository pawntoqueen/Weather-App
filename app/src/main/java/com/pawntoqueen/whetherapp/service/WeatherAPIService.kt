package com.pawntoqueen.whetherapp.service

import com.mrcaracal.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/weather?q=bing%C3%B6l&APPID=c88d7b4a42fd342e9d8e6bc08be88738

class WeatherAPIService {
    private val BASE_URL = "https://api.openweathermap.org/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Single<WeatherModel>{
        return api.getData(cityName)
    }

}