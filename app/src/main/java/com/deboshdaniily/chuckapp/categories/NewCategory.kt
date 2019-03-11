package com.deboshdaniily.chuckapp.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.R
import kotlinx.android.synthetic.main.activity_new_category.*

class NewCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_category)

        btn_cancel_new.setOnClickListener { finish() }
    }
}
