package com.example.project_jihad.ui.barang_keluar

import android.R
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project_jihad.BarangSpinnerAdapter
import com.example.project_jihad.BarangViewModel
import com.example.project_jihad.JenisBarangSpinnerAdapter
import com.example.project_jihad.SupplierSpinnerAdapter
import com.example.project_jihad.databinding.ActivityAddBarangKeluarBinding
import com.example.project_jihad.model.Barang
import com.example.project_jihad.model.JenisBarang
import com.example.project_jihad.model.ResponseInputBarangKeluar
import com.example.project_jihad.model.Supplier
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBarangKeluarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBarangKeluarBinding
    private lateinit var loginSharedPref : SharedPreferences
    private lateinit var barangViewModel : BarangViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBarangKeluarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        barangViewModel = ViewModelProvider(this).get(BarangViewModel::class.java)
        addBarangKeluar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addBarangKeluar() {


        var idBarangKeluar  = ""
        val edtAddJumlahBarangKeluar = binding.edtAddJumlahBarangKeluar
        val edtAddDeskripsiBarangKeluar = binding.edtAddDeskripsiBarangKeluar

        val listBarang = ArrayList<Barang>()
        barangViewModel.getBarangFromServer(loginSharedPref.getString("id",null))
        barangViewModel.responseBarang.observe(this, Observer {
            val data = it.body()?.data
            for (i in data!!){
             val barang = Barang(i?.id.toString(),i?.namaBarang.toString())
                listBarang.add(barang)
            }
            val spinnerAdapter = BarangSpinnerAdapter(this, R.layout.simple_spinner_item, listBarang)
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddIdBarangKeluar.adapter = spinnerAdapter

            binding.spinnerAddIdBarangKeluar.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        val selectedBarang =
                            binding.spinnerAddIdBarangKeluar.selectedItem as Barang
                        for (barang in listBarang) {
                            if (barang.namaBarang == selectedBarang.namaBarang) {
                                idBarangKeluar = barang.id!!
                                break

                            }

                        }
                        Toast.makeText(this@AddBarangKeluarActivity, "ID Barang yang dipilih: $idBarangKeluar", Toast.LENGTH_SHORT).show()
                        binding.spinnerAddIdBarangKeluar.setSelection(
                            spinnerAdapter.getPosition(
                                selectedBarang
                            )
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do something when nothing is selected
                    }
                }

        })








        binding.btnAddBarangKeluarToServer.setOnClickListener {
            when {
                edtAddDeskripsiBarangKeluar.text.toString()
                    .isEmpty() -> edtAddDeskripsiBarangKeluar.error = "Mohon isi bagian deskripsi"

                edtAddJumlahBarangKeluar.text.toString().isEmpty() -> edtAddJumlahBarangKeluar.error =
                    "Mohon isi berapa jumlah barang yang masuk"

                else -> {
                    postBarangMasukToServer(

                        jumlahBarangKeluar = edtAddJumlahBarangKeluar.text.toString(),
                        deskripsiBarangKeluar = edtAddDeskripsiBarangKeluar.text.toString(),
                        idBarangKeluar = idBarangKeluar
                    )
                }
            }
        }

    }

    private fun postBarangMasukToServer(
        jumlahBarangKeluar: String,
        deskripsiBarangKeluar: String,
        idBarangKeluar: String
    ) {
        binding.loadingAddBarangKeluar.visibility = View.VISIBLE
        binding.layoutAddBarangKeluar.visibility = View.INVISIBLE
        val getIdUser = loginSharedPref.getString("id", null)
        val api = RetrofitClient().retrofitApi
        api.inputBarangKeluar(
           jumlahBarang = jumlahBarangKeluar, deskripsi = deskripsiBarangKeluar, idBarang = idBarangKeluar, idUser = getIdUser
        ).enqueue(object : Callback<ResponseInputBarangKeluar> {
            override fun onResponse(
                call: Call<ResponseInputBarangKeluar>,
                response: Response<ResponseInputBarangKeluar>
            ) {
                if (response.isSuccessful){
                    binding.loadingAddBarangKeluar.visibility = View.INVISIBLE
                    binding.layoutAddBarangKeluar.visibility = View.VISIBLE
                    Toast.makeText(this@AddBarangKeluarActivity,"Barang keluar berhasil ditambahkan",
                        Toast.LENGTH_SHORT).show()
                }else{
                    binding.loadingAddBarangKeluar.visibility = View.INVISIBLE
                    binding.layoutAddBarangKeluar.visibility = View.VISIBLE
                    Toast.makeText(this@AddBarangKeluarActivity,"Barang keluar gagal ditambahkan,jumlah barang keluar melebihi stok barang",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseInputBarangKeluar>, t: Throwable) {
                binding.loadingAddBarangKeluar.visibility = View.INVISIBLE
                binding.layoutAddBarangKeluar.visibility = View.VISIBLE
                Toast.makeText(this@AddBarangKeluarActivity,"Barang keluar gagal ditambahkan,tolong periksa internetmu",
                    Toast.LENGTH_SHORT).show()
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