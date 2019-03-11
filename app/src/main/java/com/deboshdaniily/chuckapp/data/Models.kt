package com.deboshdaniily.chuckapp.data

import com.google.gson.annotations.SerializedName

typealias Category = String

data class JokeModel(

    @SerializedName("id")
    val jokeId: String? = null,

    @SerializedName("value")
    val joke: String,

    @SerializedName("icon_url")
    val iconUrl: String? = null,

    @SerializedName("url")
    val jokeUrl: String? = null,

    @SerializedName("category")
    val categories: List<Category>? = null
)

data class QueryResult(
    @SerializedName("total")
    val total: Int,

    @SerializedName("result")
    val result: List<JokeModel>
)
