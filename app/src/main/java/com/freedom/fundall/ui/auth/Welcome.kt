package com.freedom.fundall.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.freedom.fundall.R
import com.freedom.fundall.utils.launchActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        setupNavigation()
    }

    fun setupNavigation(){
        signupwc.setOnClickListener { launchActivity<Register>() }
        loginwc.setOnClickListener { launchActivity<Login>() }
    }
}
