package com.example.project_jihad

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_jihad.model.ResponseGetBarang
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangViewModel : ViewModel() {
    private val _responseBarang = MutableLiveData<Response<ResponseGetBarang>>()
     val responseBarang  : LiveData<Response<ResponseGetBarang>> = _responseBarang

     fun getBarangFromServer(idUser : String?){
        val api = RetrofitClient().retrofitApi
        api.getBarang(idUser).enqueue(object : Callback<ResponseGetBarang>{
            override fun onResponse(
                call: Call<ResponseGetBarang>,
                response: Response<ResponseGetBarang>
            ) {
                if (response.isSuccessful){
                    _responseBarang.value = response
                }
            }

            override fun onFailure(call: Call<ResponseGetBarang>, t: Throwable) {
            Log.e("gagal",t.message.toString())
            }

        })
    }
}