package com.example.project_jihad.ui.barang

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_jihad.adapter.BarangAdapter
import com.example.project_jihad.databinding.ActivityBarangBinding
import com.example.project_jihad.model.DataItemBarang
import com.example.project_jihad.model.ResponseGetBarang
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBarangBinding
    private lateinit var loginSharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getBarangFromServer()
    }

    private fun getBarangFromServer(){
        binding.loadingBarang.visibility = View.VISIBLE
        binding.rvBarang.visibility = View.INVISIBLE
        val getIdUser = loginSharedPref.getString("id",null)
        val api = RetrofitClient().retrofitApi
        api.getBarang(getIdUser).enqueue(object  : Callback<ResponseGetBarang>{
            override fun onResponse(
                call: Call<ResponseGetBarang>,
                response: Response<ResponseGetBarang>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.data
                    val list = ArrayList<DataItemBarang?>()
                    for (i in data!!){
                        list.add(i)
                    }
                    val adapter = BarangAdapter(list)
                    binding.rvBarang.adapter = adapter
                    binding.rvBarang.layoutManager = LinearLayoutManager(this@BarangActivity)
                    binding.loadingBarang.visibility = View.INVISIBLE
                    binding.rvBarang.visibility = View.VISIBLE
                }else{
                    Toast.makeText(this@BarangActivity,"Gagal menampilkan barang,karena data tidak ada",Toast.LENGTH_SHORT).show()
                    binding.loadingBarang.visibility = View.INVISIBLE
                    binding.rvBarang.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseGetBarang>, t: Throwable) {
                Toast.makeText(this@BarangActivity,"Gagal menampilkan barang,periksa internetmu",Toast.LENGTH_SHORT).show()
                binding.loadingBarang.visibility = View.INVISIBLE
                binding.rvBarang.visibility = View.VISIBLE
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