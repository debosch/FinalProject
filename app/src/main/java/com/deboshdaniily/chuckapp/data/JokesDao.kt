package com.deboshdaniily.chuckapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JokesDao {

    @Query("SELECT * FROM JokeEntity")
    fun getAll(): List<JokeEntity>

    @Insert
    fun insertJokeEntity(entity: JokeEntity)

    @Query("SELECT * FROM JokeEntity WHERE id = :id")
    fun getJokeById(id: Int): JokeEntity

    @Query("SELECT * FROM JokeEntity WHERE joke = :joke")
    fun getJokeByText(joke: String): List<JokeEntity>

    @Insert
    fun fillWithTestData(vararg jokeEntities: JokeEntity)

    @Query("""
        DELETE FROM JokeEntity
    """)
    fun flushDatabase()

}