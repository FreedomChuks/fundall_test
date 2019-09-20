@file:Suppress("UNCHECKED_CAST")

package com.freedom.fundall.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

//SharedPrefence Extention Method
inline fun <reified T:Any> SharedPreferences.putData(Key:String,value:T){
    val editor = this.edit()
    when (T::class) {
        Boolean::class -> editor.putBoolean(Key, value as Boolean)
        Float::class -> editor.putFloat(Key, value as Float)
        String::class -> editor.putString(Key, value as String)
        Int::class -> editor.putInt(Key, value as Int)
        Long::class -> editor.putLong(Key, value as Long)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(Key, value as Set<String>)
            }
        }
    }
    editor.commit()
}

inline fun <reified T> SharedPreferences.get(Key:String,defaultValue:T):T
{
    when(T::class){
        Boolean::class->return this.getBoolean(Key,defaultValue as Boolean) as T
        Float::class->return this.getFloat(Key,defaultValue as Float) as T
        String::class->return this.getString(Key,defaultValue as String) as T
        Int::class->return this.getInt(Key,defaultValue as Int) as T
        Long::class->return this.getLong(Key,defaultValue as Long) as T
        else->{
            if (defaultValue is Set<*>){
              return this.getStringSet(Key,defaultValue as Set<String>) as T
            }
        }
    }
    return defaultValue
}

