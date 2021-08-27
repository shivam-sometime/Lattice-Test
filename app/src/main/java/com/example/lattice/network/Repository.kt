package com.example.lattice.network

import com.example.lattice.ui.registration.model.Pincode
import com.example.lattice.ui.registration.model.PincodeItem
import com.example.lattice.ui.weathertoday.model.Weather
import com.ioniccloud.network.SafeApiRequest
import javax.inject.Singleton

@Singleton
class Repository(
    val api: ApiService
) : SafeApiRequest() {

    suspend fun getPincodeInfo(pincode : String) : List<PincodeItem> {
        return apiRequest {
            api.getPincode(pincode)
        }
    }

    suspend fun getWeather(cityName : String): Weather {
        return apiRequest {
            api.getWeather(cityName,"no")
        }
    }
}