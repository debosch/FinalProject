package com.deboshdaniily.chuckapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.categories.Category
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, NewJoke::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        menu!!.findItem(R.id.categories_menu_item).setOnMenuItemClickListener {
            val intent = Intent(this, Category::class.java)
            startActivity(intent)
            return@setOnMenuItemClickListener false
        }

        return super.onCreateOptionsMenu(menu)
    }

}
