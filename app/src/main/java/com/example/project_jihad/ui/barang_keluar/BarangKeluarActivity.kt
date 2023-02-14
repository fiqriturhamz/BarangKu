package com.example.project_jihad.ui.barang_keluar

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_jihad.DataItemBarangKeluar
import com.example.project_jihad.adapter.BarangKeluarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.project_jihad.ResponseGetBarangKeluar
import com.example.project_jihad.databinding.ActivityBarangKeluarBinding
import com.example.project_jihad.network.RetrofitClient

class BarangKeluarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBarangKeluarBinding
    private lateinit var loginSharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarangKeluarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnToAddActivityBarangKeluar.setOnClickListener {
            val intent = Intent(this, AddBarangKeluarActivity::class.java)
            startActivity(intent)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loginSharedPref =getSharedPreferences("login_session", MODE_PRIVATE)
        getBarangKeluarFromServer()
    }


    private fun getBarangKeluarFromServer(){
        binding.loadingBarangKeluar.visibility = View.VISIBLE
        binding.rvBarangKeluar.visibility = View.INVISIBLE
        binding.btnToAddActivityBarangKeluar.visibility = View.INVISIBLE
        val getIdUser =  loginSharedPref.getString("id",null)
        val api = RetrofitClient().retrofitApi
        api.getBarangKeluar(getIdUser).enqueue(object : Callback<ResponseGetBarangKeluar> {
            override fun onResponse(
                call: Call<ResponseGetBarangKeluar>,
                response: Response<ResponseGetBarangKeluar>
            ) {
                if(response.isSuccessful){
                    val getData = response.body()?.data
                    val list = ArrayList<DataItemBarangKeluar?>()
                    for (i in getData!!){
                        list.add(i)
                    }
                    val adapter = BarangKeluarAdapter(list)
                    binding.rvBarangKeluar.adapter = adapter
                    binding.rvBarangKeluar.layoutManager = LinearLayoutManager(this@BarangKeluarActivity)
                    binding.loadingBarangKeluar.visibility = View.INVISIBLE
                    binding.rvBarangKeluar.visibility = View.VISIBLE
                    binding.btnToAddActivityBarangKeluar.visibility = View.VISIBLE

                }else{
                    Toast.makeText(this@BarangKeluarActivity,"tidak ada data barang keluar",Toast.LENGTH_SHORT).show()
                    binding.loadingBarangKeluar.visibility = View.INVISIBLE
                    binding.rvBarangKeluar.visibility = View.VISIBLE
                    binding.btnToAddActivityBarangKeluar.visibility = View.VISIBLE
                }


            }

            override fun onFailure(call: Call<ResponseGetBarangKeluar>, t: Throwable) {
                Toast.makeText(this@BarangKeluarActivity,"tidak dapat menampilkan data barang keluar,periksa internetmu",Toast.LENGTH_SHORT).show()
                binding.loadingBarangKeluar.visibility = View.INVISIBLE
                binding.rvBarangKeluar.visibility = View.VISIBLE
                binding.btnToAddActivityBarangKeluar.visibility = View.VISIBLE
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