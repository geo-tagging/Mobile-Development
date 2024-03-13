package com.dicoding.geotaggingjbg.di

import android.content.Context
import com.dicoding.geotaggingjbg.data.Repository
import com.dicoding.geotaggingjbg.data.database.AppDatabase
import com.dicoding.geotaggingjbg.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val dao = database.dao()
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(dao, apiService)
    }
}