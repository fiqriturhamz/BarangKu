package com.example.project_jihad.ui.barang_masuk

import android.R
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_jihad.SupplierSpinnerAdapter
import com.example.project_jihad.SupplierViewModel
import com.example.project_jihad.adapter.BarangMasukAdapter
import com.example.project_jihad.databinding.ActivityBarangMasukBinding
import com.example.project_jihad.model.DataItemBarangMasuk
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.ResponseGetBarangMasuk
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangMasukActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBarangMasukBinding
    private lateinit var loginSharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarangMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.btnToAddActivityBarangMasuk.setOnClickListener {
            val intent = Intent(this, AddBarangMasukActivity::class.java)
            startActivity(intent)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBarangMasuk()

    }

    override fun onResume() {
        super.onResume()
        getBarangMasuk()
    }
    private fun getBarangMasuk(){
        binding.loadingBarangMasuk.visibility = View.VISIBLE
        binding.rvBarangMasuk.visibility = View.INVISIBLE
        binding.btnToAddActivityBarangMasuk.visibility = View.INVISIBLE
       val getIdUser =  loginSharedPref.getString("id",null)
        val api = RetrofitClient().retrofitApi
        api.getBarangMasuk(getIdUser).enqueue(object  : Callback<ResponseGetBarangMasuk>{
            override fun onResponse(
                call: Call<ResponseGetBarangMasuk>,
                response: Response<ResponseGetBarangMasuk>
            ) {
                if(response.isSuccessful){
                    val getData = response.body()?.data
                    val list = ArrayList<DataItemBarangMasuk?>()
                    for (i in getData!!){
                        list.add(i)
                    }
                    val adapter = BarangMasukAdapter(list)
                    binding.rvBarangMasuk.adapter = adapter
                    binding.rvBarangMasuk.layoutManager = LinearLayoutManager(this@BarangMasukActivity)
                    binding.loadingBarangMasuk.visibility = View.INVISIBLE
                    binding.rvBarangMasuk.visibility = View.VISIBLE
                    binding.btnToAddActivityBarangMasuk.visibility = View.VISIBLE

                }else{
                    binding.loadingBarangMasuk.visibility = View.INVISIBLE
                    binding.rvBarangMasuk.visibility = View.VISIBLE
                    binding.btnToAddActivityBarangMasuk.visibility = View.VISIBLE
                }


            }

            override fun onFailure(call: Call<ResponseGetBarangMasuk>, t: Throwable) {
                binding.loadingBarangMasuk.visibility = View.INVISIBLE
                binding.rvBarangMasuk.visibility = View.VISIBLE
                binding.btnToAddActivityBarangMasuk.visibility = View.VISIBLE
            }

        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}