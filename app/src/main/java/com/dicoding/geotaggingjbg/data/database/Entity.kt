package com.dicoding.geotaggingjbg.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "geo_db")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val image: String? = null,
    @ColumnInfo(name = "longitude")
    var longitude: String? = null,
    @ColumnInfo(name = "latitude")
    var latitude: String? = null,
    @ColumnInfo(name = "elevasi")
    var elevasi: String? = null,
    @ColumnInfo(name = "jenTan")
    var jenTan: Int,
    @ColumnInfo(name = "lokasi")
    var lokasi: Int,
    @ColumnInfo(name = "tanggal")
    var tanggal: Date,
    @ColumnInfo(name = "kegiatan")
    var kegiatan: Int,
    @ColumnInfo(name = "sk")
    var sk: Int
)
