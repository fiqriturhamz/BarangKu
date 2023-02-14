package com.example.project_jihad.ui.supplier

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project_jihad.SupplierSpinnerAdapter
import com.example.project_jihad.SupplierViewModel
import com.example.project_jihad.databinding.ActivityAddSuplierBinding
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.ResponseInputSupplier
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSuplierActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSuplierBinding
    private lateinit var loginSharedPref: SharedPreferences
    private lateinit var supplierSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSuplierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.btnAddSupplierToServer.setOnClickListener {
            inisialisasi()
        }
        binding.loadingAddSupplier.visibility = View.INVISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)




    }

    private fun inisialisasi() {

        val edtSupplierName = binding.edtAddSupplierName
        val edtSupplierCompany = binding.edtAddSupplierCompany

        when {
            edtSupplierName.text.isEmpty() -> edtSupplierName.error =
                "Nama Supplier tidak boleh kosong"
            edtSupplierCompany.text.isEmpty() -> edtSupplierCompany.error =
                "Nama Company tidak boleh kosong"
            else -> {
                postSupplierToServer(
                    edtSupplierName.text.toString(),
                    edtSupplierCompany.text.toString()
                )
            }
        }

    }

    private fun postSupplierToServer(supplierName: String, supplierCompany: String) {

        binding.addSupplierLayout.visibility = View.INVISIBLE
        binding.loadingAddSupplier.visibility = View.VISIBLE
        val getId = loginSharedPref.getString("id", null)
        val api = RetrofitClient().retrofitApi
        api.inputSupplier(
            supplierName = supplierName,
            companyName = supplierCompany,
            idUser = getId
        ).enqueue(object : Callback<ResponseInputSupplier> {
            override fun onResponse(
                call: Call<ResponseInputSupplier>,
                response: Response<ResponseInputSupplier>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Supplier berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.addSupplierLayout.visibility = View.VISIBLE
                    binding.loadingAddSupplier.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseInputSupplier>, t: Throwable) {
                Toast.makeText(
                    baseContext,
                    "Gagal menambahkan supplier ,periksa koneksi internetmu",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("gagal add supplier",t.message.toString())
                binding.addSupplierLayout.visibility = View.VISIBLE
                binding.loadingAddSupplier.visibility = View.INVISIBLE
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