package com.dicoding.geotaggingjbg.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.Entity
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository, id: Int): ViewModel() {
    private val _detailUser = MutableLiveData<Entity>()
    var detailUser: LiveData<Entity> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val getData: LiveData<Entity> = repository.getById(id)

    fun getDataDetail(imagePath: String){
        viewModelScope.launch {
            _isLoading.postValue(true)
//            val image = repository.
        }
    }
//    fun getData(id: Int): LiveData<Entity> {
//        return repository.getById(id)
//    }
}