package com.freedom.fundall.ui.Account

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freedom.fundall.model.AuthResponse
import com.freedom.fundall.repository.AccountRepository
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.get
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AccountViewModel(val repository: AccountRepository,application: Application): AndroidViewModel(application) {
    val getAuthUser: LiveData<Resource<AuthResponse?>> = repository.getSession()
    private val context = getApplication<Application>().applicationContext
    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("mypref", Context.MODE_PRIVATE)
    }
//getting token for localstorage
    //Todo pratice is to encrypt it but to reduce complexities i cant
    val getToken = sharedPreferences.get("AuthToken", "default value")

    fun getUser() {
        viewModelScope.launch {
            repository.getUser("Bearer $getToken")
        }

    }

    fun attemptImageUpload(AuthToken:String,avatar:String,file: MultipartBody.Part){
        viewModelScope.launch {
            repository.uploadimage(AuthToken,avatar,file)
        }
        Log.d("any ","=============token is $AuthToken")
    }
}