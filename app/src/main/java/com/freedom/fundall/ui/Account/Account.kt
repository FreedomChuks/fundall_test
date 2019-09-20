package com.freedom.fundall.ui.Account


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freedom.fundall.R
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.toast
import com.github.dhaval2404.imagepicker.ImagePicker
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
        profile_image.setOnClickListener{changeImage()}
        SubscibeObserver()
    }

    fun changeImage(){
        ImagePicker.with(this)
            .galleryOnly()
//.2
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Activity.RESULT_OK -> {
                val bytefile=data?.data
                Glide.with(this).load(bytefile).into(profile_image)
                return
            }
            ImagePicker.RESULT_ERROR -> context?.toast("error getting image file")
            else -> context?.toast("Task Cancelled ")
        }
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
