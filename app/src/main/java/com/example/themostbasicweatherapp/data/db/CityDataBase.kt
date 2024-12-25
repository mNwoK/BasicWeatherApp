package com.example.themostbasicweatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themostbasicweatherapp.data.db.entities.Cities

@Database(
    version = 1,
    entities = [Cities::class]
)
abstract class CityDataBase: RoomDatabase() {
    abstract fun getDao(): CityDao
}
