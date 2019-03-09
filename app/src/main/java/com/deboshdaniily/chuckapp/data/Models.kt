package com.deboshdaniily.chuckapp.data

import com.google.gson.annotations.SerializedName

typealias Category = String

data class JokeModel(
    @SerializedName("icon_url")
    val iconUrl: String? = null,

    @SerializedName("id")
    val jokeId: String,

    @SerializedName("url")
    val jokeUrl: String,

    @SerializedName("value")
    val joke: String,

    @SerializedName("category")
    val categories: MutableList<Category>? = null
)

data class QueryResult(
    @SerializedName("total")
    val total: Int,

    @SerializedName("result")
    val result: List<JokeModel>
)