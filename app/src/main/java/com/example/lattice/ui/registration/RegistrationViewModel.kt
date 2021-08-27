package com.example.lattice.ui.registration

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lattice.network.Repository
import com.example.lattice.ui.registration.model.Pincode
import com.example.lattice.ui.registration.model.PincodeItem
import com.ioniccloud.network.ApiException
import com.ioniccloud.network.NoInternetException
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class RegistrationViewModel(
    val repository: Repository
) : ViewModel() {

    var enterPincode = MutableLiveData<String>()
    var district = MutableLiveData<String>()
    var state = MutableLiveData<String>()

    var registationButtonClicked = MutableLiveData<Boolean>()

    var mobileNumber = MutableLiveData<String>()
    var fullName = MutableLiveData<String>()
    var dob = MutableLiveData<String>()
    var addressLine1 = MutableLiveData<String>()
    var addressLine2 = MutableLiveData<String>()

    var calendarClick = MutableLiveData<Boolean>()

    val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    var errorString = MutableLiveData<String>()

    var pinCodeResponse : MutableLiveData<List<PincodeItem>> ?= null

    fun getPinCodeResponse() : LiveData<List<PincodeItem>>{
        if(pinCodeResponse == null){
            pinCodeResponse = MutableLiveData()
        }
        return pinCodeResponse!!
    }

    fun checkPinCode(){

        if(enterPincode.value == null || TextUtils.isEmpty(enterPincode.value.toString().trim())){
            errorString.postValue("Please enter pincode")
            return
        }

        if(enterPincode.value.toString().trim().length != 6){
            errorString.postValue("Pincode of must be 6 digits")
            return
        }

        viewModelScope.launch {

            try{

                _progressBarVisibility.postValue(1)

                pinCodeResponse!!.value = repository.getPincodeInfo(enterPincode.value.toString())

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

    fun onClickOfRegistration(){

        if(mobileNumber.value == null || TextUtils.isEmpty(mobileNumber.value.toString().trim())){

            errorString.postValue("Please enter mobile number")
            return
        }

        if( mobileNumber.value!!.length !=10 ){

            errorString.postValue("Please enter mobile number of 10 digits")
            return
        }

        if(fullName.value == null || TextUtils.isEmpty(fullName.value.toString().trim())){

            errorString.postValue("Please enter FullName")
            return
        }

        if(fullName.value.toString().length > 50){
            errorString.postValue("Fullname must be of 50 char")
            return
        }

        if(dob.value == null || TextUtils.isEmpty(dob.value.toString().trim())){
            errorString.postValue("Please enter dob")
            return
        }

        if(addressLine1.value == null || TextUtils.isEmpty(addressLine1.value.toString().trim())){
            errorString.postValue("Please enter Address Line 1")
            return
        }

        if(addressLine1.value.toString().trim().length < 3 ){
            errorString.postValue("Address Line 1 must be of min 3 char")
            return
        }

        if(addressLine1.value.toString().trim().length > 50){
            errorString.postValue("Address Line 1 must be of max 50 char")
            return
        }

        if(addressLine2.value == null || TextUtils.isEmpty(addressLine2.value.toString().trim())){
            errorString.postValue("Please enter Address Line 2")
            return
        }

        if(addressLine2.value.toString().trim().length > 50){
            errorString.postValue("Address Line 2 must be of max 50 char")
            return
        }

        if(enterPincode.value == null || TextUtils.isEmpty(enterPincode.value.toString().trim())){
            errorString.postValue("Please enter pincode")
            return
        }

        if(enterPincode.value.toString().trim().length != 6){
            errorString.postValue("Pincode of must be 6 digits")
            return
        }


        registationButtonClicked.postValue(true)


    }

    fun onClickOfCalender(){
        calendarClick.postValue(true)
    }
}