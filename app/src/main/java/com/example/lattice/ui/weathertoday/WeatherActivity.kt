package com.ioniccloud.ui.activity.dummy

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lattice.BaseActivity
import com.example.lattice.R
import com.example.lattice.databinding.ActivityWeatherBinding


class WeatherActivity : BaseActivity() {

    lateinit var binding: ActivityWeatherBinding
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        setTitle(getString(R.string.weather_today))


        viewModel = ViewModelProviders.of(this,weatherViewModelFactory).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.progressBarVisibility.observe(this, Observer {
            if(it == 1){
                showProgressDialog()
            }else{
                hideProgressDialog()
            }
        })

        viewModel.errorString.observe(this,{
            snackbar(it)
        })

        viewModel.getWeatherResponse()!!.observe(this,{

            if(it.error == null){

                val current = it.current
                val location = it.location
                if(current != null){

                    viewModel.tempF.postValue(current.feelslike_f.toString())
                    viewModel.tempC.postValue(current.feelslike_c.toString())
                }

                if(location != null){
                    viewModel.latitude.postValue(location.lat.toString())
                    viewModel.longitude.postValue(location.lon.toString())
                }

            }else{
                snackbar(it.error.message)
            }

        })

    }
}