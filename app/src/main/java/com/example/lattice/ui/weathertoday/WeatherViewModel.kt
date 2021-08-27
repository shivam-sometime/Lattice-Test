package com.ioniccloud.ui.activity.dummy

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lattice.network.Repository
import com.example.lattice.ui.weathertoday.model.Weather
import com.ioniccloud.network.ApiException
import com.ioniccloud.network.NoInternetException
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class WeatherViewModel(
    val repository: Repository
) : ViewModel(){

    var cityName = MutableLiveData<String>()
    var tempF = MutableLiveData<String>()
    var tempC = MutableLiveData<String>()
    var latitude = MutableLiveData<String>()
    var longitude = MutableLiveData<String>()

    var errorString = MutableLiveData<String>()

    val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    var weatherResponse : MutableLiveData<Weather>? = null

    fun getWeatherResponse(): LiveData<Weather>?{
        if(weatherResponse == null){
            weatherResponse = MutableLiveData()
        }
        return weatherResponse
    }


    fun onClickOfShowResult(){

        if(cityName.value == null || TextUtils.isEmpty(cityName.value.toString().trim())){
            errorString.postValue("Please enter city name")
            return
        }

        viewModelScope.launch {

            try {

                _progressBarVisibility.postValue(1)

                if(weatherResponse == null){
                    weatherResponse = MutableLiveData()
                }

                weatherResponse!!.value = repository.getWeather(cityName.value.toString().trim())

                _progressBarVisibility.postValue(0)


            }catch (e: ApiException) {
                _progressBarVisibility.postValue(0)
                errorString.postValue(e.message!!)
            } catch (e: NoInternetException) {
                _progressBarVisibility.postValue(0)
                errorString.postValue(e.message!!)
            } catch (e: SocketTimeoutException) {
                _progressBarVisibility.postValue(0)
                errorString.postValue(e.message!!)
            } catch (e: Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
    }

}