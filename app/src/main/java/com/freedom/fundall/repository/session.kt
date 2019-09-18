package com.freedom.fundall.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.freedom.fundall.model.AuthResponse
import com.freedom.fundall.utils.Resource

class session: MediatorLiveData<Resource<AuthResponse?>>() {
    init {
        value=Resource.init()
    }

    internal fun clear() {
        postValue(Resource.init())
    }

    internal fun loading() {
        postValue(Resource.Loading())
    }

    internal fun set(response: AuthResponse?) {
        postValue(Resource.Success(response))
    }

    internal fun error(error:String) {
        postValue(Resource.Failure(error))
    }


}