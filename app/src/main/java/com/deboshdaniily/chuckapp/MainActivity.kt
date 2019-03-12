package com.deboshdaniily.chuckapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deboshdaniily.chuckapp.data.DataService
import com.deboshdaniily.chuckapp.data.DataServiceImpl
import com.deboshdaniily.chuckapp.jokes.NewJoke
import com.deboshdaniily.chuckapp.ui.JokeAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

private const val JOKES_LIMIT = 50
private const val QUERY_MIN_LENGTH = 3

private const val SCREEN_FROM_INTERNET = 0
private const val SCREEN_CACHED = 1
private const val SCREEN_WRITTEN = 2


class MainActivity : AppCompatActivity() {

    private lateinit var service: DataService
    private var screen: Int = 0
        set(value) {
            field = value
            when (value) {
                SCREEN_FROM_INTERNET -> switchToInternet()
                SCREEN_CACHED -> switchToCached()
                SCREEN_WRITTEN -> switchToWritten()
            }
        }

    private fun postInit() {
        service = DataServiceImpl(this.applicationContext)
        setSupportActionBar(main_toolbar)

        search_bar.setImeActionLabel(getString(R.string.search), KeyEvent.KEYCODE_ENTER);
        search_bar.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                0 -> {
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        if (isNetworkAvailable()) {
                            switchToSearch(search_bar.text.toString())
                        } else {
                            Snackbar.make(v, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show()
                        }
                    }
                    true
                }
                else -> false
            }
        }

        joke_list.adapter = JokeAdapter(JOKES_LIMIT) { _, callback ->
            service.getRandomJoke(callback::invoke)
        }
        joke_list.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, NewJoke::class.java)
            startActivity(intent)
        }

        screen = if (isNetworkAvailable()) {
            SCREEN_FROM_INTERNET
        } else {
            SCREEN_CACHED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postInit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {

            R.id.search -> {
                if (search_bar.visibility == View.GONE) {
                    search_bar.visibility = View.VISIBLE
                    search_bar.requestFocus()
                } else {
                    search_bar.visibility = View.GONE
                    search_bar.clearFocus()
                }
                true
            }

            R.id.from_internet_menu_item -> {
                screen = SCREEN_FROM_INTERNET
                true
            }

            R.id.saved_menu_item -> {
                screen = SCREEN_CACHED
                true
            }

            R.id.written_menu_item -> {
                screen = SCREEN_WRITTEN
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun switchToInternet() {
        main_toolbar.subtitle = getString(R.string.subtitle_from_internet)
        if (isNetworkAvailable()) {
            joke_list.adapter = JokeAdapter(JOKES_LIMIT) { _, callback ->
                service.getRandomJoke(callback::invoke)
            }
        } else {
            // TODO debosh: no internet connection available
            joke_list.adapter = JokeAdapter(1) { _, callback ->
                service.getRandomJoke(callback::invoke)
            }
        }
    }

    private fun switchToCached() {
        main_toolbar.subtitle = getString(R.string.subtitle_cached)
        service.getCachedJokesCount {
            if (it.isSuccess) {
                val count = it.get()
                runOnUiThread {
                    joke_list.adapter = JokeAdapter(count) { position, callback ->
                        service.getCachedJokeById(position + 1) { runOnUiThread { callback.invoke(it) } }
                    }
                }
            } else {
                // TODO debosh: could not get database, show splash screen mb?
            }
        }
    }

    private fun switchToWritten() {
        main_toolbar.subtitle = getString(R.string.subtitle_written)
        service.getWrittenJokes { tryJokes ->
            if (tryJokes.isSuccess) {
                val jokes = tryJokes.get()
                val adapter = JokeAdapter(jokes.size)
                adapter.setJokesList(jokes)
                runOnUiThread { joke_list.adapter = adapter }
            } else {
                // TODO debosh: could not get database, show splash screen mb?
            }
        }
    }

    private fun switchToSearch(query: String) {
        if (query.length >= QUERY_MIN_LENGTH) {
            service.getJokesWithQuery(query) { tryQueryResult ->
                if (tryQueryResult.isSuccess) {
                    main_toolbar.subtitle = getString(R.string.subtitle_search)
                    val queryResult = tryQueryResult.get()
                    val adapter = JokeAdapter(queryResult.total)
                    adapter.setJokesList(queryResult.result)
                    joke_list.adapter = adapter
                } else {
                    Snackbar.make(joke_list, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show()
                }
            }
        } else {
            Snackbar.make(joke_list, getString(R.string.too_little_chars_in_search_query), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable() =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true
}
