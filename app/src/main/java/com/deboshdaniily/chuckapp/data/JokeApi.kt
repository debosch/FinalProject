package com.deboshdaniily.chuckapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {

    @GET("jokes/random")
    fun getRandomJoke(): Call<JokeModel>

    @GET("jokes/random")
    fun getRandomJokeFromCategory(@Query("category") category: String): Call<JokeModel>

    @GET("jokes/search")
    fun getJokesWithQuery(@Query("query") searchQuery: String): Call<QueryResult>

    @GET("jokes/categories")
    fun getCategories(): Call<List<Category>>

}