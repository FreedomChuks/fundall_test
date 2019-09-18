package com.freedom.fundall.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freedom.fundall.R
import com.freedom.fundall.databinding.ActivityLoginBinding
import com.freedom.fundall.utils.Resource.*
import com.freedom.fundall.utils.ViewState
import com.freedom.fundall.utils.snackbar
import com.freedom.fundall.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


class Login : AppCompatActivity(),ViewState {

    override fun UIErrorMessage(message: String) {
    root.snackbar(message)
    }

    lateinit var bindings: ActivityLoginBinding
    private val authviewmodel: AuthViewModel by viewModel()
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(authviewmodel::class.java)
        bindings.authviewmodel = authviewmodel
        authviewmodel.viewState=this
        subscribeObservers()
    }

    fun subscribeObservers() {
        authviewmodel.respose.observe(this, Observer {
            when(it){
                is Loading -> toast("loading")
                is Success -> {toast("login ${it.data?.success?.user?.email}")
                    Log.d("any","========login ${it.data?.success}")
                }
                is Failure -> toast("login failed")
            }
        })
    }
}
