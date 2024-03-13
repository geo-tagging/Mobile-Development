package com.dicoding.geotaggingjbg.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.Entity

class SaveViewModel(private val repository: Repository) : ViewModel() {

    fun saveImageLocal(entity: Entity) = repository.saveImageToLocal(entity)
}