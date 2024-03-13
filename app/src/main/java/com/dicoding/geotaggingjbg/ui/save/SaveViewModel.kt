package com.dicoding.geotaggingjbg.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveViewModel(private val repository: Repository) : ViewModel() {

    fun saveImageLocal(entity: Entity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveImageToLocal(entity)
        }
    }
}