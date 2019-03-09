package com.deboshdaniily.chuckapp

import android.util.Log
import androidx.test.runner.AndroidJUnit4
import com.deboshdaniily.chuckapp.data.RetrofitDataServiceImpl
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val service = RetrofitDataServiceImpl()
    private val category = "dev"
    private val query = "chuck norris"

    @Test
    fun showCategories() {

        service.getCategories { Log.e("Test", it.toString()) }
        Thread.sleep(6000)
    }

    @Test
    fun showRandomJoke() {

        service.getRandomJoke { Log.e("Test", it.toString()) }
        Thread.sleep(6000)
    }

    @Test
    fun showRandomJokeFromCategory() {

        service.getRandomJokeFromCategory(category){ Log.e("Test", it.toString()) }
        Thread.sleep(6000)
    }

    @Test
    fun showJokesWithQuery() {

        service.getJokesWithQuery(query){ Log.e("Test", it.toString()) }
        Thread.sleep(6000)
    }



}
