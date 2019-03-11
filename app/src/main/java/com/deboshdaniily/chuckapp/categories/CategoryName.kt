package com.deboshdaniily.chuckapp.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.R
import kotlinx.android.synthetic.main.activity_category_name.*

class CategoryName : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_name)
        title = ""
        btn_cancel_name.setOnClickListener { finish() }
    }
}
