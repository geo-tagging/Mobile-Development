package com.dicoding.geotaggingjbg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.Entity

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun getData(): LiveData<List<Entity>>{
        return repository.getAllData()
    }
}