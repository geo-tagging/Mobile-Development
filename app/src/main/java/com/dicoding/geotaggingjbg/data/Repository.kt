package com.dicoding.geotaggingjbg.data

import android.app.Application
import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.geotaggingjbg.data.database.AppDatabase
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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private var dao: Dao,
    application: Application,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
) {
    init {
        val db = AppDatabase.getInstance(application)
        dao = db.dao()
    }

    fun getAllData(): LiveData<List<Entity>> {
        return dao.getAllData()
    }

    fun delete(entity: Entity) {
        executorService.execute { dao.delete(entity) }
    }

    fun saveImageToLocal(entity: Entity) {
        executorService.execute { dao.insert(entity) }
    }

    fun update(entity: Entity) {
        executorService.execute { dao.update(entity) }
    }

    fun getById(id:Int): LiveData<Entity>{
        return dao.getDatabyId(id)
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
            application: Application,
            dao: Dao,
            executorService: ExecutorService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(dao, application, executorService)
        }.also { instance = it }
    }
}