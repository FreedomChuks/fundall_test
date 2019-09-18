package com.freedom.fundall.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.freedom.fundall.R
import com.freedom.fundall.ui.auth.Welcome
import com.freedom.fundall.utils.launchActivity
import com.freedom.fundall.utils.newIntent

class SplashScreen : AppCompatActivity() {
    //implemented splashscreen the right way
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        launchActivity<Welcome> {
        finish()
        }
    }
}
