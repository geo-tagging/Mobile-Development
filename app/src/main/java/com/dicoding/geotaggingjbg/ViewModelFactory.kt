package com.dicoding.geotaggingjbg

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.di.Injection
import com.dicoding.geotaggingjbg.ui.camera.CameraViewModel
import com.dicoding.geotaggingjbg.ui.detail.DetailViewModel
import com.dicoding.geotaggingjbg.ui.home.HomeViewModel
import com.dicoding.geotaggingjbg.ui.maps.MapsFragment
import com.dicoding.geotaggingjbg.ui.maps.MapsViewModel
import com.dicoding.geotaggingjbg.ui.save.SaveViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}