package com.deboshdaniily.chuckapp.data

import io.vavr.control.Try
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DataService {

    fun getRandomJoke(callback: (Try<JokeModel>) -> Unit)

    fun getRandomJokeFromCategory(category: String, callback: (Try<JokeModel>) -> Unit)

    fun getJokesWithQuery(searchQuery: String, callback: (Try<QueryResult>) -> Unit)

    fun getCategories(callback: (Try<List<Category>>) -> Unit)

}

class RetrofitDataServiceImpl : DataService{

    private val jokeApiRetrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(JokeApi::class.java)

    override fun getRandomJoke(callback: (Try<JokeModel>) -> Unit) {

        jokeApiRetrofit.getRandomJoke().enqueue(

            object: Callback<JokeModel> {

                override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getRandomJokeFromCategory(category: String, callback: (Try<JokeModel>) -> Unit) {
        jokeApiRetrofit.getRandomJokeFromCategory(category).enqueue(
            object: Callback<JokeModel> {

                override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getJokesWithQuery(searchQuery: String, callback: (Try<QueryResult>) -> Unit) {
        jokeApiRetrofit.getJokesWithQuery(searchQuery).enqueue(
            object: Callback<QueryResult> {

                override fun onFailure(call: Call<QueryResult>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<QueryResult>, response: Response<QueryResult>) {
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getCategories(callback: (Try<List<Category>>) -> Unit) {
        jokeApiRetrofit.getCategories().enqueue(
            object: Callback<List<Category>> {

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }


}