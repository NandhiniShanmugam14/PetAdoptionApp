package com.example.petadoptionapplication

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.logout).setOnClickListener()
        {
            val sharedPreferences=getSharedPreferences("user",Context.MODE_PRIVATE)
            val editor= sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}