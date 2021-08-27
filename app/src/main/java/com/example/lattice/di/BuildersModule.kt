package com.ioniccloud.di



import com.example.lattice.ui.registration.RegistrationActivity
import com.ioniccloud.ui.activity.dummy.WeatherActivity
import com.ioniccloud.ui.activity.dummy.WeatherViewModelFactory
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeWelcomeActivity(): RegistrationActivity

    @ContributesAndroidInjector()
    abstract fun contributeWeatherActivity(): WeatherActivity
}