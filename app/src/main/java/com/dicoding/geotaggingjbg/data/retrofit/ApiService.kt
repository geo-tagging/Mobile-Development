package com.dicoding.geotaggingjbg.data.retrofit

import com.dicoding.geotaggingjbg.data.response.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("tanaman")
    suspend fun uploadImageObject(
        @Part picture: MultipartBody.Part,
    ): Response
}