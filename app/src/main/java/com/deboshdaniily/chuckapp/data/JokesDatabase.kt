package com.deboshdaniily.chuckapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_NAME = "jokesLocalStorage"
private const val DATABASE_VERSION = 1

@Database(entities = [JokeEntity::class], version = DATABASE_VERSION)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun jokesDao(): JokesDao

    companion object {

        @Volatile
        private var instance: JokesDatabase? = null

        fun getInstance(context: Context): JokesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): JokesDatabase {
            return Room.databaseBuilder(context, JokesDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}

