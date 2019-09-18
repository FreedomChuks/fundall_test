package com.freedom.fundall.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.freedom.fundall.R
import kotlinx.android.synthetic.main.loadingdialog.*

class LoadingDialoge:DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingScreenTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater.inflate(R.layout.loadingdialog,container,false)
//        Glide.with(context!!).load(R.drawable.loader).into(loader_view)
        return view
    }
}

