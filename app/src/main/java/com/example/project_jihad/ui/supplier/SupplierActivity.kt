package com.example.project_jihad.ui.supplier

import android.R

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.project_jihad.SupplierData

import com.example.project_jihad.adapter.SupplierAdapter
import com.example.project_jihad.databinding.ActivitySupplierBinding
import com.example.project_jihad.model.DataItemGetSupplier

import com.example.project_jihad.model.ResponseGetSupplier
import com.example.project_jihad.model.Supplier
import com.example.project_jihad.network.RetrofitClient


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierBinding
    private lateinit var loginSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.btnAddSuplier.setOnClickListener {
            val intent = Intent(this@SupplierActivity, AddSuplierActivity::class.java)
            startActivity(intent)

        }
        getSupplierFromServer()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onResume() {
        super.onResume()
        getSupplierFromServer()
    }

    private fun getSupplierFromServer() {
        binding.swipeRefresh.isRefreshing = true
        val api = RetrofitClient().retrofitApi
        api.getDataSupplier(loginSharedPref.getString("id", null))
            .enqueue(object : Callback<ResponseGetSupplier> {
                override fun onResponse(
                    call: Call<ResponseGetSupplier>,
                    response: Response<ResponseGetSupplier>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        val listSupplier = ArrayList<Supplier>()
                        val list = ArrayList<DataItemGetSupplier?>()


                        for (i in data!!) {
                            val supplier = Supplier(i?.id,i?.namaSupplier)
                            listSupplier.add(supplier)
                            list.add(i)
                        }
                        val supplierData = SupplierData.getInstance()
                        supplierData.setData(listSupplier)


                        val adapter = SupplierAdapter(list)
                        binding.rvSupplier.adapter = adapter
                        binding.rvSupplier.layoutManager =
                            LinearLayoutManager(this@SupplierActivity)
                        binding.swipeRefresh.isRefreshing = false
                    } else {
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(
                            this@SupplierActivity,
                            "Tidak ada data supplier",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }

                override fun onFailure(call: Call<ResponseGetSupplier>, t: Throwable) {
                    Log.e("gagal", t.message.toString())
                }

            })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}