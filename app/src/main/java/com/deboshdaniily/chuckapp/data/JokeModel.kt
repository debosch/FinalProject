package com.deboshdaniily.chuckapp.data

import com.google.gson.annotations.SerializedName
import java.util.*

class JokeModel {

    @SerializedName("icon_url")
    var iconUrl: String? = null

    @SerializedName("id")
    lateinit var jokeId: String

    @SerializedName("url")
    lateinit var jokeUrl: String

    @SerializedName("value")
    lateinit var joke: String

    @SerializedName("category")
    var categories: Array<String>? = null

    override fun toString(): String {
        return "JokeModel(iconUrl='$iconUrl', jokeId='$jokeId', jokeUrl='$jokeUrl', joke='$joke', categories=${Arrays.toString(
            categories
        )})"
    }
}