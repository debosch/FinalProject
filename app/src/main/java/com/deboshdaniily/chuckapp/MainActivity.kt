package com.deboshdaniily.chuckapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deboshdaniily.chuckapp.data.DataService
import com.deboshdaniily.chuckapp.data.DataServiceImpl
import com.deboshdaniily.chuckapp.jokes.NewJoke
import com.deboshdaniily.chuckapp.ui.JokeAdapter
import kotlinx.android.synthetic.main.activity_main.*

private const val JOKES_LIMIT = 50

class MainActivity : AppCompatActivity() {

    lateinit var service: DataService

    private fun postInit() {
        service = DataServiceImpl(this.applicationContext)

        joke_list.adapter = JokeAdapter(JOKES_LIMIT) { _, callback ->
            service.getRandomJoke(callback::invoke)
        }
        joke_list.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postInit()

        fab.setOnClickListener {
            val intent = Intent(this, NewJoke::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        menu?.findItem(R.id.from_internet_menu_item)?.setOnMenuItemClickListener {
            joke_list.adapter = JokeAdapter(JOKES_LIMIT) { _, callback ->
                service.getRandomJoke(callback::invoke)
            }
            true
        }

        menu?.findItem(R.id.saved_menu_item)?.setOnMenuItemClickListener {
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
            true
        }

        menu?.findItem(R.id.written_menu_item)?.setOnMenuItemClickListener {
            service.getWrittenJokes { tryJokes ->
                if (tryJokes.isSuccess) {
                    val jokes = tryJokes.get()
                    val adapter = JokeAdapter(jokes.size) { _, _ -> }
                    adapter.setJokesList(jokes)
                    runOnUiThread { joke_list.adapter = adapter }
                } else {
                    // TODO debosh: could not get database, show splash screen mb?
                }
            }
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}
