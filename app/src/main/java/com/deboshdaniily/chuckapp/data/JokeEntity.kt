package com.deboshdaniily.chuckapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JokeEntity(

    @PrimaryKey val id: Int?,

    @ColumnInfo(name = "remote_id") val remoteId: String? = null,

    @ColumnInfo(name = "joke") val joke: String,

    @ColumnInfo(name = "icon_url") val iconUrl: String? = null,

    @ColumnInfo(name = "joke_url") val jokeUrl: String? = null,

    @ColumnInfo(name = "categories") val categories: String? = null
) {
    fun toModel() : JokeModel = JokeModel(
        remoteId,
        joke,
        iconUrl,
        jokeUrl,
        categories?.split(", ")
    )

    companion object {
        fun fromJokeModel(model: JokeModel): JokeEntity {
            return JokeEntity(
                null,
                model.jokeId,
                model.joke,
                model.iconUrl,
                model.jokeUrl,
                model.categories?.joinToString(", ")
            )
        }
    }
}
