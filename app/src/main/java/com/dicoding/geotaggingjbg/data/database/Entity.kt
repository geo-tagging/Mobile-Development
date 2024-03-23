package com.dicoding.geotaggingjbg.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Entity(tableName = "geo_db")
@Parcelize
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
    var jenTan: Int = 0,
    @ColumnInfo(name = "lokasi")
    var lokasi: Int = 0,
    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null,
    @ColumnInfo(name = "kegiatan")
    var kegiatan: Int = 0,
    @ColumnInfo(name = "sk")
    var sk: Int = 0
) : Parcelable
