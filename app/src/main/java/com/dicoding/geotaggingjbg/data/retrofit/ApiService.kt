package com.dicoding.geotaggingjbg.data.retrofit

import com.dicoding.geotaggingjbg.data.response.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.sql.Date

interface ApiService {
    @Multipart
    @POST("tanaman")
    suspend fun uploadImageObject(
        @Part ("gambar")picture: MultipartBody.Part,
        @Part("longitude") longitude: String?,
        @Part("latitude") latitude: String?,
        @Part("elevasi") elevasi: String?,
        @Part("jenTan") jenTan: Int,
        @Part("lokasi") lokasi: Int,
        @Part("tanggal") tanggal: Date,
        @Part("kegiatan") kegiatan: Int,
        @Part("sk") sk: Int
    ): Response
}