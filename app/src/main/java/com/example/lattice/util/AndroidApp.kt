package com.example.lattice.util

import android.app.Application
import com.ioniccloud.di.AppComponent
import com.ioniccloud.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AndroidApp : Application(), HasAndroidInjector {


    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    companion object{
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)


    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


}