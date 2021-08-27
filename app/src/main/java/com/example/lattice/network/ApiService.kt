package com.example.lattice.network

import com.example.lattice.ui.registration.model.Pincode
import com.example.lattice.ui.registration.model.PincodeItem
import com.example.lattice.ui.weathertoday.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("pincode/{pincode}")
    suspend fun getPincode(
        @Path(value = "pincode", encoded = true) pincode: String
    ): Response<List<PincodeItem>>

    @GET("https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("aqi") aqi : String
    ) : Response<Weather>


}