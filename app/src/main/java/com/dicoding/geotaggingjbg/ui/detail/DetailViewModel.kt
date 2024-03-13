package com.dicoding.geotaggingjbg.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.Entity
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {
    private val _detailUser = MutableLiveData<Entity>()
    val detailUser: LiveData<Entity> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDataDetail(imagePath: String){
        viewModelScope.launch {
            _isLoading.postValue(true)
//            val image = repository.
        }
    }
}