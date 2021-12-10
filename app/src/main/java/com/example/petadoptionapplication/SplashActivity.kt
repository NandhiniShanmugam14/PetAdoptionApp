package com.example.petadoptionapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val token=""


        Handler(Looper.getMainLooper()).postDelayed({
            if(token.isEmpty())
            {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 4000)

        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        val image = findViewById<ImageView>(R.id.imageView2)
        val text1 = findViewById<TextView>(R.id.textView2)
        val text2 = findViewById<TextView>(R.id.textView3)

        image.animation = topAnim
        text1.animation = bottomAnim
        text2.animation = bottomAnim




    }
}