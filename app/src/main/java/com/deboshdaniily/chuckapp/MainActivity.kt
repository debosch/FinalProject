package com.deboshdaniily.chuckapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.deboshdaniily.chuckapp.data.DataService
import com.deboshdaniily.chuckapp.data.DataServiceImpl

class MainActivity : AppCompatActivity() {

    lateinit var service: DataService

    private fun postInit() {
        service = DataServiceImpl(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postInit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}
