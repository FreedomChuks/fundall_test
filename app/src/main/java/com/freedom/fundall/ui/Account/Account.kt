package com.freedom.fundall.ui.Account


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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
import com.freedom.fundall.ui.LoadingDialoge
import com.freedom.fundall.utils.Resource
import com.freedom.fundall.utils.toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.florent37.inlineactivityresult.InlineActivityResult.startForResult
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import kotlinx.android.synthetic.main.fragment_account.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class Account : Fragment() {
    private val accountViewModel: AccountViewModel by viewModel()
    lateinit var viewmodel:AccountViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var requestimage:MultipartBody.Part
    lateinit var  loadingDialoge: LoadingDialoge

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


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profile_image.setOnClickListener{changeImage()}
        sharedPreferences=activity!!.applicationContext.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        SubscibeObserver()
    }

    fun changeImage(){
        val AuthToken=sharedPreferences.getString("AuthToken","Token")
//        val requestBody:RequestBody=
//            "avatar".toRequestBody("multipart/form-data".toMediaTypeOrNull())


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
                is Resource.Loading -> {showDialog()}
                is Resource.Success -> {
                    hideLoader()
                    context?.toast("loaded data")
                    setDetails(it.data?.success?.data?.email,it.data?.success?.data?.firstname,it.data?.success?.data?.monthly_target.toString())
                    if (it.data?.success?.user?.avatar ==null){
                        Glide.with(this).load(it.data?.success?.data?.avatar).into(profile_image)
                        return@Observer
                    }else{
                        Glide.with(this).load(it.data.success.url).into(profile_image)
                    }

                }
                is Resource.Failure -> {hideLoader();context?.toast("error loading data")}
            }
        })

    }

    fun setDetails(email:String?,firstname:String?,amount:String?){
        emailforuser.text=email
        firstnameforuser.text=firstname
        amountforuser.text=amount
    }

    private fun showDialog(){
        loadingDialoge.show(activity!!.supportFragmentManager,"loadingScreen")
    }

    private fun hideLoader(){
        loadingDialoge.dismiss()
    }

}
