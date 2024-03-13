package com.dicoding.geotaggingjbg.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null){
                synchronized(AppDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app_database")
                        .build()
                }
            }
            return  INSTANCE as AppDatabase
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    application,
//                    AppDatabase::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
        }
    }
}