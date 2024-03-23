package com.dicoding.geotaggingjbg.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Update
    fun update(entity: Entity)

    @Delete
    fun delete(entity: Entity)

    @Query("SELECT * FROM geo_db")
    fun getAllData(): LiveData<List<Entity>>

    @Query("SELECT * FROM geo_db WHERE id = :id")
    fun getDatabyId(id:Int): LiveData<Entity>
}