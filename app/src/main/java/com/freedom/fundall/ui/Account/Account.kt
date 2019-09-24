package com.freedom.fundall.ui.Account


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.freedom.fundall.R
import com.freedom.fundall.ui.LoadingDialoge
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.get
import com.freedom.fundall.utils.toast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_account.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class Account : Fragment() {
    private val accountViewModel: AccountViewModel by viewModel()
    lateinit var viewmodel:AccountViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var requestimage:MultipartBody.Part
    lateinit var  loadingDialoge: LoadingDialoge
    lateinit var requestOptions: RequestOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialoge= LoadingDialoge()
        viewmodel=ViewModelProvider(this)[accountViewModel::class.java]
        viewmodel.getUser()
        requestOptions= RequestOptions()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profile_image.setOnClickListener{changeImage()}
        sharedPreferences=activity!!.applicationContext.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        SubscibeObserver()
    }

    fun changeImage(){
        val AuthToken=sharedPreferences.getString("AuthToken","Token")


        ImagePicker.with(this)
            .crop(1f, 1f)               //Crop Square image(Optional)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .start { resultCode, data ->
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data
                        val file: File = ImagePicker.getFile(data)!!

                        val request:RequestBody=RequestBody.create("multipart/form-data".toMediaTypeOrNull(),file)
                        requestimage=MultipartBody.Part.createFormData("avatar",file.name,request)

                        viewmodel.attemptImageUpload("Bearer $AuthToken","avatar",requestimage)

                        profile_image.setImageURI(fileUri)
return@start
                    }
                    ImagePicker.RESULT_ERROR -> context?.toast(ImagePicker.getError(data))
                    else -> context?.toast("Task Cancelled")
                }
            }

    }


    fun SubscibeObserver(){
        viewmodel.getAuthUser.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {Glide.with(this).applyDefaultRequestOptions(requestOptions)}
                is Resource.Success -> {
                    context?.toast("loaded data")
                    setDetails()
                    if (it.data?.success?.data?.avatar==null){
                        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(it.data?.success?.url).into(profile_image)
                        return@Observer
                    }else{
                        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(it.data?.success?.data.avatar).into(profile_image)
                    }

                }
                is Resource.Failure -> {hideLoader();context?.toast("loading error data")}
            }
        })

    }

    fun setDetails(){
        emailforuser.text=sharedPreferences.get("Email","xxxxx@gmail.com")
        firstnameforuser.text=sharedPreferences.get("Username","defaultdata")
    }

    private fun showDialog(){
        loadingDialoge.show(activity!!.supportFragmentManager,"loadingScreen")
    }

    private fun hideLoader(){
        loadingDialoge.dismiss()
    }

}
