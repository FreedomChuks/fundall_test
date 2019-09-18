package com.freedom.fundall.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

inline fun<reified T:Any> newIntent(activity: Activity):Intent= Intent(activity,T::class.java)

fun Context.toast(@StringRes message: Int){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}


fun Context.toast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun View.snackbar(@StringRes message:Int){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}


fun View.snackbar(message:String){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}

inline  fun <reified T:Any> Activity.launchActivity(
    noinline init:Intent.()->Unit={}){
    val intent=newIntent<T>(this)
    intent.init()
    startActivity(intent)
}


