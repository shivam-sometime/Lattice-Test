package com.example.lattice.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lattice.network.Repository
import javax.inject.Singleton

@Singleton
class RegistrationViewModelFactory(
    val repository: Repository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegistrationViewModel(repository) as T
    }
}