package com.dicoding.geotaggingjbg.di

import android.app.Application
import android.content.Context
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.AppDatabase
import com.dicoding.geotaggingjbg.data.retrofit.ApiConfig
import java.util.concurrent.Executors

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val dao = database.dao()
        val apiService = ApiConfig.getApiService()
        val executorService = Executors.newSingleThreadExecutor()
        return Repository.getInstance(context.applicationContext as Application)
    }
}