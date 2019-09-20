package com.freedom.fundall.ui.Account


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freedom.fundall.R
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.toast
import kotlinx.android.synthetic.main.fragment_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class Account : Fragment() {
    private val accountViewModel: AccountViewModel by viewModel()
    lateinit var viewmodel:AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel=ViewModelProvider(this)[accountViewModel::class.java]
        viewmodel.getUser()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SubscibeObserver()
    }


    fun SubscibeObserver(){
        viewmodel.getAuthUser.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> context?.toast("loading")
                is Resource.Success -> {
                    context?.toast("loaded data")
                    Log.d("Any","link  ${it.data?.success?.data?.avatar}")
                    Glide.with(this).load(it.data?.success?.data?.avatar).into(profile_image)
                }
                is Resource.Failure -> context?.toast("error loading data")
            }
        })
    }
}
