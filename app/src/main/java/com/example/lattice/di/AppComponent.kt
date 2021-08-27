package com.ioniccloud.di

import android.app.Application
import com.example.lattice.util.AndroidApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =  [AndroidInjectionModule::class,BuildersModule::class,AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: AndroidApp)
}