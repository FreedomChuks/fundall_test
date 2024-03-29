package com.freedom.fundall.repository

import androidx.lifecycle.LiveData
import com.freedom.fundall.api.ApiServices
import com.freedom.fundall.model.AuthResponse
import com.freedom.fundall.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.HttpException


class AccountRepository (val apiServices: ApiServices,val sessions:session){
 fun getSession():LiveData<Resource<AuthResponse?>> = sessions

    suspend fun getUser(AuthToken:String){
        try {
            sessions.loading()
            withContext(IO){
                val response=apiServices.getUser(AuthToken)
                if (response.isSuccessful){
                    sessions.set(response.body())
                    return@withContext
                }
                sessions.error(response.message())
            }

        }catch (e:Exception){
          sessions.error(e.message!!)
        }catch (H:HttpException){
            sessions.error(H.message())
        }
    }

    suspend fun uploadimage(AuthToken: String,avatar:String,file:MultipartBody.Part){
        try {
            withContext(IO){
                sessions.loading()
                val response=apiServices.updateAvatar(AuthToken,avatar,file)
                if (response.isSuccessful){
                    sessions.set(response.body())
                    return@withContext
                }
                sessions.error(response.message())
            }
        }catch (e:Exception){
            sessions.error(e.message!!)
        }catch (H:HttpException){
            sessions.error(H.message())
        }
    }
}