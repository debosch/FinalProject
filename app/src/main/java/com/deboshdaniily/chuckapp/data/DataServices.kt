package com.deboshdaniily.chuckapp.data

import android.content.Context
import io.vavr.control.Try
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

interface DataService {

    fun getRandomJoke(callback: (Try<JokeModel>) -> Unit = {})

    fun getRandomJokeFromCategory(category: String, callback: (Try<JokeModel>) -> Unit = {})

    fun getJokesWithQuery(searchQuery: String, callback: (Try<QueryResult>) -> Unit = {})

    fun getCategories(callback: (Try<List<Category>>) -> Unit = {})

    fun getCachedJokesCount(callback: (Try<Int>) -> Unit = {})

    fun getCachedJokeById(id: Int, callback: (Try<JokeModel>) -> Unit = {})

    fun isThisJokeCached(model: JokeModel, callback: (Try<Boolean>) -> Unit = {})

    fun cacheJoke(model: JokeModel, callback: (Try<Void>) -> Unit = {})
}

class DataServiceImpl(context: Context) : DataService {
    private val jokeApiRetrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(JokeApi::class.java)

    private val localJokesDAO = JokesDatabase.getInstance(context).jokesDao()

    override fun getRandomJoke(callback: (Try<JokeModel>) -> Unit) {

        jokeApiRetrofit.getRandomJoke().enqueue(

            object : Callback<JokeModel> {

                override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                    val model = response.body()
                    if (model != null) {
                        isThisJokeCached(model) {
                            // val cached = it.get()
                            if (it.isSuccess && !it.get()) {
                                cacheJoke(model)
                            }
                        }
                    }
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getRandomJokeFromCategory(category: String, callback: (Try<JokeModel>) -> Unit) {
        jokeApiRetrofit.getRandomJokeFromCategory(category).enqueue(
            object : Callback<JokeModel> {

                override fun onFailure(call: Call<JokeModel>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<JokeModel>, response: Response<JokeModel>) {
                    val model = response.body()
                    if (model != null) {
                        isThisJokeCached(model) {
                            // val cached = it.get()
                            if (it.isSuccess && !it.get()) {
                                cacheJoke(model)
                            }
                        }
                    }
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getJokesWithQuery(searchQuery: String, callback: (Try<QueryResult>) -> Unit) {
        jokeApiRetrofit.getJokesWithQuery(searchQuery).enqueue(
            object : Callback<QueryResult> {

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
            object : Callback<List<Category>> {

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    callback.invoke(Try.failure(t))
                }

                override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                    callback.invoke(Try.success(response.body()))
                }
            }
        )
    }

    override fun getCachedJokesCount(callback: (Try<Int>) -> Unit) {
        thread {
            callback.invoke(Try.of { localJokesDAO.getAll().size })
        }
    }

    override fun getCachedJokeById(id: Int, callback: (Try<JokeModel>) -> Unit) {
        thread {
            callback.invoke(Try.of { localJokesDAO.getJokeById(id).toModel() })
        }
    }

    override fun isThisJokeCached(model: JokeModel, callback: (Try<Boolean>) -> Unit) {
        thread {
            callback.invoke(Try.of { localJokesDAO.getJokeByText(model.joke).isNotEmpty() })
        }
    }

    override fun cacheJoke(model: JokeModel, callback: (Try<Void>) -> Unit) {
        thread {
            callback.invoke(Try.run { localJokesDAO.insertJokeEntity(JokeEntity.fromJokeModel(model)) })
        }
    }
}
