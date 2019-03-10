package com.deboshdaniily.chuckapp.jokes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.R
import com.deboshdaniily.chuckapp.categories.NewCategory
import kotlinx.android.synthetic.main.activity_new_joke.*

class NewJoke : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_joke)
        title = "Write new joke"
        btn_cancel.setOnClickListener { finish() }

        edit_category.setOnClickListener {
            val intent = Intent(this, NewCategory::class.java)
            startActivity(intent)
        }

    }
}
