package com.example.project_jihad

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_jihad.adapter.SupplierAdapter
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.ResponseGetSupplier
import com.example.project_jihad.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await





class SupplierViewModel : ViewModel() {
   private  var _response = MutableLiveData<Response<ResponseGetSupplier>>()
    val response :LiveData<Response<ResponseGetSupplier>> = _response
    private var _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _layout = MutableLiveData<Boolean>()
    val layout : LiveData<Boolean> = _layout
    fun getSupplierFromServer(idUser: String?) {

        val api = RetrofitClient().retrofitApi
        api.getDataSupplier(idUser).enqueue(object :
            Callback<ResponseGetSupplier> {
            override fun onResponse(
                call: Call<ResponseGetSupplier>,
                response: Response<ResponseGetSupplier>
            ) {
                if (response.isSuccessful) {
                    _response.value = response
                    _loading.value = false
                    _layout.value = true
                }
                else{
                    _loading.value = false
                    _layout.value = false
                }

            }

            override fun onFailure(call: Call<ResponseGetSupplier>, t: Throwable) {
                _loading.value = false
                _layout.value = false

            }

        })

    }
}
