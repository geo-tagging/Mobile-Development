package com.dicoding.geotaggingjbg.data.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("skppkh")
	val skppkh: String,

	@field:SerializedName("plant_id")
	val plantId: Int,

	@field:SerializedName("id_lokasi")
	val idLokasi: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("id_sk")
	val idSk: Int,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id_jenis")
	val idJenis: Int,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("kegiatan")
	val kegiatan: String,

	@field:SerializedName("elevasi")
	val elevasi: String,

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("longitude")
	val longitude: String
)
