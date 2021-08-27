package com.example.lattice

import android.app.Dialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lattice.ui.registration.RegistrationViewModelFactory

import com.google.android.material.snackbar.Snackbar
import com.ioniccloud.ui.activity.dummy.WeatherViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    @Inject lateinit var weatherViewModelFactory: WeatherViewModelFactory

    var dialog: Dialog?=null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun showProgressDialog() {
        if (dialog == null)
            dialog = Dialog(this)
        dialog!!.setContentView(R.layout.progress_dialog)
        dialog!!.setCancelable(false)

        if (dialog != null && !dialog!!.isShowing)
            dialog!!.show()
    }

    protected fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    fun snackbar(message: String){
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}