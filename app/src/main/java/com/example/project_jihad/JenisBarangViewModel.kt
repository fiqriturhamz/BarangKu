package com.example.project_jihad

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_jihad.model.ResponseGetJenisBarang
import com.example.project_jihad.model.ResponseGetSupplier
import com.example.project_jihad.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await



class JenisBarangViewModel : ViewModel() {
    private val _response = MutableLiveData<Response<ResponseGetJenisBarang>>()
    val response :LiveData<Response<ResponseGetJenisBarang>> = _response

    private val _loadingProgressBar = MutableLiveData<Boolean>()
    val loadingProgressBar : LiveData<Boolean> = _loadingProgressBar

    private val _layoutVisibility = MutableLiveData<Boolean>()
    val layoutVisibility : LiveData<Boolean> = _layoutVisibility

    fun getJenisBarangFromServer(idUser:String?){
        val api = RetrofitClient().retrofitApi
        api.getAllJenisBarang(idUser).enqueue(object  : Callback<ResponseGetJenisBarang>{
            override fun onResponse(
                call: Call<ResponseGetJenisBarang>,
                response: Response<ResponseGetJenisBarang>
            ) {
                if (response.isSuccessful){
                    _response.value = response
                    _loadingProgressBar.value = false
                    _layoutVisibility.value = true

                }
                else{
                    _loadingProgressBar.value = true
                    _layoutVisibility.value= false
                }
            }

            override fun onFailure(call: Call<ResponseGetJenisBarang>, t: Throwable) {
              Log.e("gagal",t.message.toString())
            }

        })

    }
}
