package com.freedom.fundall.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freedom.fundall.model.AuthResponse
import com.freedom.fundall.repository.AuthRepository
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.ViewState
import com.freedom.fundall.utils.get
import kotlinx.coroutines.launch


class AuthViewModel constructor(val repository: AuthRepository):ViewModel(){
    var email:String?=null
    var password:String?=null
    var confirmPass:String?=null
    var firstname:String?=null
    var lastname:String?=null
    var viewState:ViewState?=null
    lateinit var sharedPreferences: SharedPreferences
    val response: LiveData<Resource<AuthResponse?>> =repository.getAuthResource()


    val getUsername=sharedPreferences?.get("Username","data")
    private val getEmail=sharedPreferences?.get("Username","data")

    fun login(){
        if (email.isNullOrEmpty()) {
            viewState?.UIErrorMessage("email cant be empty")
            return
        }

        if (!isEmailValid(email!!)){
            viewState?.UIErrorMessage("invalid email format ")
            return
        }else if(password.isNullOrEmpty()){
            viewState?.UIErrorMessage("password cant be empty")
            return
        }

       attemptLogin()

    }

    fun ReLogin(){
        if (getEmail.isNullOrEmpty()){
            viewState?.UIErrorMessage("username cant be empty")
            return
        }else if(password.isNullOrEmpty()){
            viewState?.UIErrorMessage("password cant be empty")
            return
        }
        attemptLogin()

    }

    fun signup(){
        if(email.isNullOrBlank()){
            viewState?.UIErrorMessage("email cant be empty")
            return
        }else if(!isEmailValid(email!!)){
            viewState?.UIErrorMessage("invalid email format")
            return
        }
        else if(firstname.isNullOrEmpty()){
            viewState?.UIErrorMessage("firstname cant be empty")
            return
        }else if (lastname.isNullOrBlank()){
            viewState?.UIErrorMessage("lastname cant be empty")
            return
        }else if(password.isNullOrEmpty()){
            viewState?.UIErrorMessage("password cant be empty")
            return
        }else if(confirmPass.isNullOrBlank()){
            viewState?.UIErrorMessage("confirm password")
            return
        }
        else if(!comparePassword(password!!,confirmPass!!)){
            viewState?.UIErrorMessage("password doesnt match")
            return
        }
        attemptSignup()
    }

//a basic and simple password compare check
    fun comparePassword(password:String,confirmpass:String):Boolean{
        if (password.contentEquals(confirmpass)){
            return true
        }
    return false
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun attemptLogin(){
       viewModelScope.launch {
           repository.LoginUser(email!!,password!!)
       }
    }

    fun attemptSignup(){
        viewModelScope.launch {
            repository.SignupUser(firstname!!,lastname!!,email!!,password!!,confirmPass!!)
        }
    }




}



