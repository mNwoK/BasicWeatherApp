package com.example.themostbasicweatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themostbasicweatherapp.data.db.entities.Cities
import com.example.themostbasicweatherapp.data.db.entities.Weather

@Database(
    version = 1,
    entities = [Cities::class, Weather::class]
)
abstract class CityDataBase: RoomDatabase() {
    abstract fun getDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE : CityDataBase? = null

        fun getInstance(context: Context) : CityDataBase {
            var instance = INSTANCE
            if (instance==null) {
                instance= Room.databaseBuilder(
                    context.applicationContext,
                    CityDataBase::class.java,
                    "cityDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE=instance
            }
            return instance
        }
    }
}