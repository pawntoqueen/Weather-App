package com.pawntoqueen.whetherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pawntoqueen.whetherapp.R
import com.pawntoqueen.whetherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var viewModel : MainViewModel

//
private lateinit var GET: SharedPreferences
private lateinit var SET: SharedPreferences.Editor



//https://api.openweathermap.org/data/2.5/weather?q=bing%C3%B6l&APPID=c88d7b4a42fd342e9d8e6bc08be88738
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "ankara")
        edt_city_name.setText(cName)

        viewModel.refreshData()

        getLiveData()
    }

    private fun getLiveData() {
        viewModel.weather_data.observe(this, Observer { data ->
            data.let{
                ll_data.visibility = View.GONE
                tv_degree.text = data.main.temp.toString() + "°C"

            } })
    }
}