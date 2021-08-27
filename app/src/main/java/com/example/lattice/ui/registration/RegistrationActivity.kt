package com.example.lattice.ui.registration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.lattice.BaseActivity
import com.example.lattice.R
import com.example.lattice.databinding.ActivityRegistrationBinding
import com.ioniccloud.ui.activity.dummy.WeatherActivity
import java.text.SimpleDateFormat
import java.util.*

class RegistrationActivity : BaseActivity() {

    lateinit var binding : ActivityRegistrationBinding
    lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration)

        setTitle(getString(R.string.register))

        viewModel = ViewModelProviders.of(this,registrationViewModelFactory).get(RegistrationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.errorString.observe(this,{
            snackbar(it)
        })

        viewModel.progressBarVisibility.observe(this,{
            if(it == 1){
                showProgressDialog()
            }else{
                hideProgressDialog()
            }
        })

        viewModel.registationButtonClicked.observe(this,{
            startActivity(Intent(this,WeatherActivity::class.java))
        })

        viewModel.calendarClick.observe(this,{

            val currentDate = Calendar.getInstance()
            val date = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    date[year, monthOfYear] = dayOfMonth

                    val d: String = getDateAndTime(date.time)
                    viewModel.dob.postValue(getDesiredDateFormat(d))

                },
                currentDate[Calendar.YEAR],
                currentDate[Calendar.MONTH],
                currentDate[Calendar.DATE]
            )

            val futureDate = Calendar.getInstance()
            datePickerDialog.datePicker.maxDate = futureDate.timeInMillis - 1000

            datePickerDialog.show()

        })

        viewModel.getPinCodeResponse().observe(this,{
            val res = it.get(0)

            if(res.Status.equals("Success")){

                val postOffice = res.PostOffice
                if(postOffice.size > 0){

                    val district = postOffice.get(0).District
                    val state = postOffice.get(0).State

                    viewModel.district.value = district
                    viewModel.state.value = state

                }

            } else if(res.Status.equals("Error")){

                viewModel.district.value = ""
                viewModel.state.value = ""

                snackbar(res.Message)
            }
        })

    }

    fun getDateAndTime (date : Date) : String{
        val formattedDate = SimpleDateFormat("yyyy-MM-dd hh.mm aa").format(date)
        return formattedDate
    }


    fun getDesiredDateFormat(inputDate : String) : String{
        try {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd hh.mm aa")
            val targetFormat = SimpleDateFormat("dd/MM/yyyy")
            val date: Date = originalFormat.parse(inputDate)
            val formattedDate: String = targetFormat.format(date) // 20120821
            return formattedDate
        }catch (e : Exception){
            e.printStackTrace()
        }
        return ""
    }
}