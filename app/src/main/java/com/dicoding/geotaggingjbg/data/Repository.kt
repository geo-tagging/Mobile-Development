package com.dicoding.geotaggingjbg.data

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.geotaggingjbg.data.database.Dao
import com.dicoding.geotaggingjbg.data.database.Entity
import com.dicoding.geotaggingjbg.data.response.Response
import com.dicoding.geotaggingjbg.data.retrofit.ApiService
import com.dicoding.geotaggingjbg.ui.utils.ResultState
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class Repository(
    private val dao: Dao,
    private val apiService: ApiService
) {
    fun getAllData(): LiveData<List<Entity>>{
        return dao.getAllImages()
    }

    suspend fun saveImageToLocal(entity: Entity){
        dao.upsertImage(entity)
    }

//    fun uploadObjectImage(picture: File) = liveData {
//        emit(ResultState.Loading)
//        val requestImageFile = picture.asRequestBody("image/jpeg".toMediaType())
//        val multipartBody = MultipartBody.Part.createFormData(
//            "picture", picture.name, requestImageFile
//        )
//        try {
//            val successResponse = apiService.uploadImageObject(multipartBody)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, Response::class.java)
//            emit(ResultState.Error(errorResponse.message))
//        }
//    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            dao : Dao,
            apiService: ApiService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(dao, apiService)
        }.also { instance = it }
    }
}