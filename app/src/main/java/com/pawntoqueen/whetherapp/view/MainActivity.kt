package com.pawntoqueen.whetherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
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

        viewModel.refreshData(cName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)

            swipe_refresh_layout.isRefreshing = false
        }

        img_search_city.setOnClickListener{
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName!!)
            getLiveData()

        }
    }

    private fun getLiveData() {
        viewModel.weather_data.observe(this, Observer { data ->
            data.let{
                ll_data.visibility = View.VISIBLE
                tv_degree.text = data.main.temp.toString() + "°C"
                pb_loading.visibility = View.GONE
                tv_city_code.text = data.sys.country.toString()
                tv_city_name.text = data.name.toString()
                tv_humidity.text = data.main.humidity.toString()
                tv_wind_speed.text = data.wind.speed.toString()
                tv_lat.text = data.coord.lat.toString()
                tv_lon.text = data.coord.lon.toString()

                Glide.with(this)
                    .load("http://openweathermap.org/img/wn" + data.weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures)


            }
        })
        viewModel.weather_load.observe(this, { load ->
            load?.let {
                if (it){
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                }
                else{
                    pb_loading.visibility = View.GONE
                }
            }

        })
        viewModel.weather_error.observe(this, { error ->
            error?.let {
                if (it){
                    pb_loading.visibility = View.GONE
                    tv_error.visibility = View.VISIBLE
                    ll_data.visibility = View.GONE
                }
                else{
                    tv_error.visibility = View.GONE
                }
            }

        })
    }
}