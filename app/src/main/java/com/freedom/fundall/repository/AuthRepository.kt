package com.freedom.fundall.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.freedom.fundall.api.ApiServices
import com.freedom.fundall.model.AuthResponse
import com.freedom.fundall.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class AuthRepository (val api:ApiServices,val usersession:session) {

    fun getAuthResource():LiveData<Resource<AuthResponse?>> = usersession

    //to standerd error handing just not to increase complexities
    suspend fun LoginUser(email:String,password:String){

        withContext(IO){
            try {

                usersession.loading()
                val response= api.login(email, password)
                if (response.isSuccessful){
                    usersession.set(response.body())
                    return@withContext
                }
                usersession.error(response.message())
            }catch (e:Exception){
               usersession.error(e.localizedMessage!!)
            }catch (h: HttpException){
                Log.d("Tag","http-error :${h.message()}")
            }


        }
    }

    //to standerd error handing just not to increase complesity
    suspend fun SignupUser(firstname:String,lastname:String,email: String,password: String,confirmpassword:String){
        withContext(IO){
            try {
                usersession.loading()
                val response= api.register(firstname,lastname,email, password, confirmpassword)
                if (response.isSuccessful){
                    usersession.set(response.body())
                    return@withContext
                }
                usersession.error(response.message())
                usersession.clear()
            }catch (e:Exception){
                usersession.error(e.localizedMessage!!)
            }catch (h: HttpException){
                Log.d("Tag","http-error :${h.message()}")
            }


        }
    }

}





