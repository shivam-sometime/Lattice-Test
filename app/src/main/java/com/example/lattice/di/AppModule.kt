package com.ioniccloud.di

import android.app.Application
import android.content.Context
import com.example.lattice.network.ApiService
import com.example.lattice.network.Repository
import com.example.lattice.ui.registration.RegistrationViewModelFactory
import com.example.lattice.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ioniccloud.ui.activity.dummy.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

@Module
internal class AppModule {

    @Provides
    fun providesContext(app: Application): Context {
        return app.getApplicationContext()
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
      //  val networkConnectionInterceptor = NetworkConnectionInterceptor(context)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)

        return builder.build()
    }


    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

        return builder.build()
    }

    @Provides
    fun providesApiServices(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java!!)
    }

    @Provides
    fun provideRegistrationViewModelFactory(repository: Repository) : RegistrationViewModelFactory {
        return RegistrationViewModelFactory(repository)
    }

    @Provides
    fun provideWeatherViewModelFactory(repository: Repository) : WeatherViewModelFactory {
        return WeatherViewModelFactory(repository)
    }

    @Provides
    fun provideRepository(apiService: ApiService) : Repository {
        return Repository(apiService)
    }

    /*


   @Provides
   fun provideUtils() : Utils {
       return Utils()
   }

  */

}