package com.deboshdaniily.chuckapp

import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.deboshdaniily.chuckapp.data.JokeModel
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    interface ApiService {
        @GET("jokes/random")
        fun getRandomJoke(): Call<JokeModel>
    }

    @Test
    fun showRandomJoke() {
        val service = retrofit.create(ApiService::class.java)
        val exec = service.getRandomJoke().execute()
        Log.e("Test", "Body: ${exec.body()}")
        Log.e("Test", "ErrorBody: ${exec.errorBody()?.string()}")
    }
}
