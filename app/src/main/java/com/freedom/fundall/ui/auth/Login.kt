package com.freedom.fundall.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freedom.fundall.MainActivity
import com.freedom.fundall.R
import com.freedom.fundall.databinding.ActivityLoginBinding
import com.freedom.fundall.ui.LoadingDialoge
import com.freedom.fundall.utils.*
import com.freedom.fundall.utils.Resource.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class Login : AppCompatActivity(),ViewState {

    override fun UIErrorMessage(message: String) {
    root.snackbar(message)
    }

    lateinit var bindings: ActivityLoginBinding
    private val authviewmodel: AuthViewModel by viewModel()
    lateinit var viewModel: AuthViewModel
    lateinit var  loadingDialoge: LoadingDialoge
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(authviewmodel::class.java)
        bindings.authviewmodel = authviewmodel
        authviewmodel.viewState=this
        sharedPreferences=applicationContext.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        loadingDialoge= LoadingDialoge()
        subscribeObservers()
    }

    fun subscribeObservers() {
        authviewmodel.response.observe(this, Observer {
            when(it){
                is Loading -> showDialog()
                is Success -> {
                    hideLoader()
                    launchActivity<MainActivity>()
                    saveUserToken("AuthToken",it.data?.success?.user?.access_token!!)
                    saveUserToken("Username",it.data.success.user.firstname)
                    saveUserToken("Email",it.data.success.user.email)
                }
                is Failure -> {
                    hideLoader()
                    toast("login failed")
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val AuthToken=sharedPreferences.getString("AuthToken","Token")
        if (!AuthToken.isNullOrEmpty()){
            launchActivity<mediator>()
        }else{
            Log.d("any","=========Username is null")
        }
    }

    private fun showDialog(){
        loadingDialoge.show(supportFragmentManager,"loadingScreen")
    }

    private fun hideLoader(){
        loadingDialoge.dismiss()
    }

    private fun saveUserToken(key:String,authToken:String){
    sharedPreferences?.putData(key,authToken)
    }
}
