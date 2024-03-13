package com.dicoding.geotaggingjbg.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.di.Injection

class HomeViewModelFactory private constructor(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(context: Context): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}