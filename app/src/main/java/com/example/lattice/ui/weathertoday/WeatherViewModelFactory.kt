package com.ioniccloud.ui.activity.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lattice.network.Repository
import javax.inject.Singleton

@Singleton
class WeatherViewModelFactory(
    val repository: Repository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}