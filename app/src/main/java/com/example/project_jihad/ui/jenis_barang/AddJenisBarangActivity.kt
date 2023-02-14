package com.example.project_jihad.ui.jenis_barang

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.project_jihad.model.ResponseInputJenisBarang
import com.example.project_jihad.databinding.ActivityAddJenisBarangBinding
import com.example.project_jihad.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddJenisBarangActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddJenisBarangBinding
    private lateinit var loginSharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJenisBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.btnAddJenisBarangToServer.setOnClickListener {
            addJenisBarang()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun addJenisBarang(){
        val edtAddJenisBarang = binding.edtAddJenisBarang
        val edtAddDeskripsiJenisBarang = binding.edtAddDeskripsiJenisBarang
        when{
            edtAddJenisBarang.text.toString().isEmpty() -> edtAddJenisBarang.error = "Masukkan Jenis Barang"
            edtAddDeskripsiJenisBarang.text.toString().isEmpty() -> edtAddDeskripsiJenisBarang.error = "Masukkan Deskripsi Jenis Barang"
            else -> postJenisBarangToServer(edtAddJenisBarang.text.toString(),edtAddDeskripsiJenisBarang.text.toString())
        }
    }

    private fun postJenisBarangToServer(jenisBarang:String,deskripsiJenisBarang:String){
        binding.addJenisBarangLayout.visibility = View.INVISIBLE
        binding.loadingAddJenisBarang.visibility = View.VISIBLE
        val api = RetrofitClient().retrofitApi
        api.inputJenisBarang(jenisBarang = jenisBarang, deskripsiBarang = deskripsiJenisBarang,loginSharedPref.getString("id",null) ).enqueue(
            object : Callback<ResponseInputJenisBarang> {
                override fun onResponse(
                    call: Call<ResponseInputJenisBarang>,
                    response: Response<ResponseInputJenisBarang>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(
                            baseContext,
                            "Jenis barang berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.addJenisBarangLayout.visibility = View.VISIBLE
                        binding.loadingAddJenisBarang.visibility = View.INVISIBLE
                        binding.edtAddJenisBarang.setText("")
                        binding.edtAddDeskripsiJenisBarang.setText("")

                    }
                    else {
                        Toast.makeText(
                            baseContext,
                            "Jenis barang gagal ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.addJenisBarangLayout.visibility = View.VISIBLE
                        binding.loadingAddJenisBarang.visibility = View.INVISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseInputJenisBarang>, t: Throwable) {
                    Toast.makeText(
                        baseContext,
                        "Jenis barang gagal ditambahkan,cek koneksi internetmu",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loadingAddJenisBarang.visibility = View.INVISIBLE
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