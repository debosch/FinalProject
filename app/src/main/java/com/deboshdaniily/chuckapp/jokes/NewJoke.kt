package com.deboshdaniily.chuckapp.jokes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.R
import com.deboshdaniily.chuckapp.data.CATEGORY_SELF_WRITTEN
import com.deboshdaniily.chuckapp.data.DataService
import com.deboshdaniily.chuckapp.data.DataServiceImpl
import com.deboshdaniily.chuckapp.data.JokeModel
import kotlinx.android.synthetic.main.activity_new_joke.*

class NewJoke : AppCompatActivity() {

    private lateinit var service: DataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_joke)
        title = resources.getString(R.string.title_new_joke)

        setSupportActionBar(new_joke_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_ok.setOnClickListener {
            val model = JokeModel(
                null,
                edit_joke.text.toString(),
                null,
                null,
                listOf(CATEGORY_SELF_WRITTEN)
            )
            service.cacheJoke(model)
            finish()
        }
        btn_cancel.setOnClickListener { finish() }

        service = DataServiceImpl(this.applicationContext)
    }
}
