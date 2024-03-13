package com.dicoding.geotaggingjbg.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface Dao {
    @Upsert
    suspend fun upsertImage(entity: Entity)

    @Query("SELECT * FROM geo_db")
    fun getAllImages(): LiveData<List<Entity>>

    @Query("DELETE FROM geo_db WHERE id =:id")
    suspend fun deleteImageById(id: Int)
}