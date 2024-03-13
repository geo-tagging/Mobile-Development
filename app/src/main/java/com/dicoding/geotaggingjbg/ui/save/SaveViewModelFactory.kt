package com.dicoding.geotaggingjbg.ui.save

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.di.Injection

class SaveViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveViewModel::class.java)) {
            return SaveViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SaveViewModelFactory? = null
        fun getInstance(context: Context): SaveViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SaveViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}