package com.freedom.fundall.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freedom.fundall.R
import com.freedom.fundall.databinding.ActivityLoginBinding
import com.freedom.fundall.databinding.ActivityRegisterBinding
import com.freedom.fundall.ui.LoadingDialoge
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.ViewState
import com.freedom.fundall.utils.snackbar
import com.freedom.fundall.utils.toast
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class Register : AppCompatActivity(),ViewState {

    override fun UIErrorMessage(message: String) {
        root.snackbar(message)
    }

    lateinit var bindings: ActivityRegisterBinding
    private val authviewmodel: AuthViewModel by viewModel()
    lateinit var viewModel: AuthViewModel
    lateinit var  loadingDialoge: LoadingDialoge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bindings = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this).get(authviewmodel::class.java)
        bindings.authviewmodel = authviewmodel
        viewModel.viewState=this
        loadingDialoge= LoadingDialoge()
        subscribeObservers()
    }

    fun subscribeObservers() {
        authviewmodel.respose.observe(this, Observer {
            when(it){
                is Resource.Loading ->{
                    showDialog()
                }
                is Resource.Success -> {
                    hideLoader()
                    toast("login ${it.data?.success?.status}")
                }
                is Resource.Failure ->{
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
}
