package com.example.project_jihad.ui.jenis_barang

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_jihad.JenisBarangViewModel
import com.example.project_jihad.adapter.JenisBarangAdapter
import com.example.project_jihad.adapter.SupplierAdapter
import com.example.project_jihad.databinding.ActivityJenisBarangBinding
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.DataItemJenisBarang
import com.example.project_jihad.model.ResponseGetJenisBarang
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JenisBarangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJenisBarangBinding
    private lateinit var loginSharedPref: SharedPreferences
    private lateinit var jenisBarangViewModel: JenisBarangViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJenisBarangBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        jenisBarangViewModel = ViewModelProvider(this)[JenisBarangViewModel::class.java]
        binding.btnAddJenisBarang.setOnClickListener {
            val intent = Intent(this, AddJenisBarangActivity::class.java)
            startActivity(intent)
        }
        getJenisBarangFromViewModel()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun getJenisBarangFromViewModel() {
        val getIdFromLogin = loginSharedPref.getString("id", null)
       loadingProgressBar(true)
       layoutVisibility(false)
        jenisBarangViewModel.getJenisBarangFromServer(getIdFromLogin)
        jenisBarangViewModel.response.observe(this, Observer {
            val data = it.body()?.data
            val list = ArrayList<DataItemJenisBarang?>()
            for (i in data!!) {
                list.add(i)
            }
            val adapter = JenisBarangAdapter(list)
            binding.rvJenisBarang.adapter = adapter
            binding.rvJenisBarang.layoutManager = LinearLayoutManager(this)

        })
        jenisBarangViewModel.layoutVisibility.observe(this, Observer {
            layoutVisibility(it)
        })
        jenisBarangViewModel.loadingProgressBar.observe(this, Observer {
            loadingProgressBar(it)
        })
    }
/*
       private fun getJenisBarangFromServer() {
           binding.loadingJenisBarang.visibility = View.VISIBLE
           binding.rvJenisBarang.visibility = View.INVISIBLE
           binding.btnAddJenisBarang.visibility = View.INVISIBLE
           val getIdFromLogin = loginSharedPref.getString("id", null)
           val api = RetrofitClient().retrofitApi
           api.getAllJenisBarang(getIdFromLogin).enqueue(object : Callback<ResponseGetJenisBarang> {
               override fun onResponse(
                   call: Call<ResponseGetJenisBarang>,
                   response: Response<ResponseGetJenisBarang>
               ) {
                   if (response.isSuccessful) {
                       val data = response.body()?.data
                       val list = ArrayList<DataItemJenisBarang?>()
                       for (i in data!!) {
                           list.add(i)
                       }
                       val adapter = JenisBarangAdapter(list)
                       binding.rvJenisBarang.adapter = adapter
                       binding.rvJenisBarang.layoutManager =
                           LinearLayoutManager(this@JenisBarangActivity)
                       binding.loadingJenisBarang.visibility = View.INVISIBLE
                       binding.rvJenisBarang.visibility = View.VISIBLE
                       binding.btnAddJenisBarang.visibility = View.VISIBLE
                   } else {
                       Toast.makeText(
                           this@JenisBarangActivity,
                           "Tidak ada data jenis barang",
                           Toast.LENGTH_SHORT
                       ).show()
                       binding.loadingJenisBarang.visibility = View.INVISIBLE
                       binding.rvJenisBarang.visibility = View.VISIBLE
                       binding.btnAddJenisBarang.visibility = View.VISIBLE
                   }
               }

               override fun onFailure(call: Call<ResponseGetJenisBarang>, t: Throwable) {
                   Toast.makeText(
                       this@JenisBarangActivity,
                       "Tidak ada data jenis barang",
                       Toast.LENGTH_SHORT
                   ).show()
                   binding.loadingJenisBarang.visibility = View.INVISIBLE
                   binding.rvJenisBarang.visibility = View.VISIBLE
                   binding.btnAddJenisBarang.visibility = View.VISIBLE
               }

           })
       }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadingProgressBar(visibility: Boolean) {
      if(visibility){
          binding.loadingJenisBarang.visibility = View.VISIBLE

      }else{
          binding.loadingJenisBarang.visibility = View.INVISIBLE
      }
    }

    private fun layoutVisibility(visibility: Boolean) {
        if(visibility){
            binding.rvJenisBarang.visibility = View.VISIBLE

        }else{
            binding.rvJenisBarang.visibility = View.INVISIBLE
        }
    }
}