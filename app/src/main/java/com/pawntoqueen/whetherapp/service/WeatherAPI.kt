package com.pawntoqueen.whetherapp.service

import com.mrcaracal.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherAPI {
    @GET("/data/2.5/weather?q=bing%C3%B6l&APPID=c88d7b4a42fd342e9d8e6bc08be88738")

    fun getData(): Single<WeatherModel>
}