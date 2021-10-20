package com.pawntoqueen.whetherapp.service

import com.mrcaracal.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather?&units=metric&APPID=c88d7b4a42fd342e9d8e6bc08be88738")
    fun getData(
        @Query("q") cityName : String
    ): Single<WeatherModel>
}