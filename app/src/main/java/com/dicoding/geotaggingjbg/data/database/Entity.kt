package com.dicoding.geotaggingjbg.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_db")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "imagePath")
    val image: String? = null
)
