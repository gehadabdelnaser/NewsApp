package com.gehad.news.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gehad.news.R
import com.gehad.news.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startHomeActivity()

    }

    private fun startHomeActivity(){
        val handler= Handler()
        handler.postDelayed(Runnable {
            val intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
        },1000)
    }
}