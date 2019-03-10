package com.deboshdaniily.chuckapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NewJoke : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_joke)
        title = "Write new joke"
    }
}
