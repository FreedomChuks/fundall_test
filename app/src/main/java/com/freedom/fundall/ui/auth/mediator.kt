package com.freedom.fundall.ui.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freedom.fundall.MainActivity
import com.freedom.fundall.R
import com.freedom.fundall.databinding.ActivityLoginBinding
import com.freedom.fundall.databinding.ActivityMediatorBinding
import com.freedom.fundall.ui.LoadingDialoge
import com.freedom.fundall.utils.*
import kotlinx.android.synthetic.main.activity_mediator.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class mediator : AppCompatActivity(),ViewState {
    override fun UIErrorMessage(message: String) {
        root.snackbar(message)
    }

    lateinit var bindings: ActivityMediatorBinding
    private val authviewmodel: AuthViewModel by viewModel()
    lateinit var viewModel: AuthViewModel
    lateinit var  loadingDialoge: LoadingDialoge
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediator)
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_mediator)
        viewModel = ViewModelProvider(this).get(authviewmodel::class.java)
        bindings.authviewmodel = authviewmodel
        authviewmodel.viewState=this
        sharedPreferences=applicationContext.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        loadingDialoge= LoadingDialoge()
        backtoWelcome.setOnClickListener {clearUser(); launchActivity<Login>();finish() }
        subscribeObservers()
    }

    fun subscribeObservers() {
        authviewmodel.response.observe(this, Observer {
            when(it){
                is Resource.Loading -> showDialog()
                is Resource.Success -> {
                    hideLoader()
                    launchActivity<MainActivity>()
                }
                is Resource.Failure -> {
                    hideLoader()
                    toast("login failed")
                }
            }
        })
    }

    private fun showDialog(){
        loadingDialoge.show(supportFragmentManager,"loadingScreen")
    }

    private fun hideLoader(){
        loadingDialoge.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
    fun clearUser(){
        sharedPreferences.putData("AuthToken","")
    }
}
