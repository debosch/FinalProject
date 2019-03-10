package com.deboshdaniily.chuckapp.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.R
import kotlinx.android.synthetic.main.category_layout.*

class Category : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_layout)
        title = "Choose category"

        btn_cancel.setOnClickListener { finish() }
    }
}
