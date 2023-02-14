package com.example.project_jihad.ui.barang_masuk

import android.R
import android.content.Intent

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project_jihad.*

import com.example.project_jihad.databinding.ActivityAddBarangMasukBinding
import com.example.project_jihad.model.DataItemJenisBarang
import com.example.project_jihad.model.JenisBarang
import com.example.project_jihad.model.ResponseInputBarangMasuk
import com.example.project_jihad.model.Supplier
import com.example.project_jihad.network.RetrofitClient
import com.example.project_jihad.ui.main.MainActivity
import com.example.project_jihad.ui.supplier.SupplierActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddBarangMasukActivity : AppCompatActivity() {
    private lateinit var loginSharedPref: SharedPreferences
    private lateinit var binding: ActivityAddBarangMasukBinding
    private lateinit var supplierViewModel: SupplierViewModel
    private lateinit var jenisBarangViewModel: JenisBarangViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBarangMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        jenisBarangViewModel = ViewModelProvider(this).get(JenisBarangViewModel::class.java)
        supplierViewModel =
            ViewModelProvider(this@AddBarangMasukActivity).get(SupplierViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        addBarangMasuk()

    }


    private fun addBarangMasuk() {


        val supplierData = SupplierData.getInstance()
        var idSupplier = ""
        var idJenisBarang = ""
        val listSupplier = supplierData.listSupplier

        if (listSupplier.isNotEmpty()) {
            val spinnerAdapter =
                SupplierSpinnerAdapter(this, android.R.layout.simple_spinner_item, listSupplier)
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddSupplierBarangMasuk.adapter = spinnerAdapter

            binding.spinnerAddSupplierBarangMasuk.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        val selectedSupplier =
                            binding.spinnerAddSupplierBarangMasuk.selectedItem as Supplier
                        for (supplier in listSupplier) {
                            if (supplier.nama == selectedSupplier.nama) {
                                idSupplier = supplier.id!!
                                break
                            }

                        }
                        binding.spinnerAddSupplierBarangMasuk.setSelection(
                            spinnerAdapter.getPosition(
                                selectedSupplier
                            )
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do something when nothing is selected
                    }
                }
        } else {
                val builder = AlertDialog.Builder(this@AddBarangMasukActivity)
                builder.setTitle("Tidak Ada Supplier")
                builder.setMessage("Data Supplier belum diambil dari server ,silahkan ke menu Supplier untuk meLoad data")
                builder.setPositiveButton("OK") { dialog, which ->
                    val intent = Intent(this@AddBarangMasukActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
        }


           jenisBarangViewModel = ViewModelProvider(this)[jenisBarangViewModel::class.java]
           jenisBarangViewModel.getJenisBarangFromServer(loginSharedPref.getString("id", null))
           val listJenisBarang = ArrayList<JenisBarang>()

           jenisBarangViewModel.response.observe(this, Observer {
               val data = it.body()?.data

               for (i in data!!) {
                   val jenisBarang = JenisBarang(i?.id.toString(), i?.jenisBarang.toString())
                   listJenisBarang.add(jenisBarang)

               }

               val spinnerAdapter =
                   JenisBarangSpinnerAdapter(
                       this,
                       R.layout.simple_spinner_item,
                       listJenisBarang
                   )
               spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
               binding.spinnerAddJenisBarangMasuk.adapter = spinnerAdapter

               binding.spinnerAddJenisBarangMasuk.onItemSelectedListener =
                   object : AdapterView.OnItemSelectedListener {
                       override fun onItemSelected(
                           parent: AdapterView<*>,
                           view: View,
                           position: Int,
                           id: Long
                       ) {
                           val selectedJenisBarang =
                               binding.spinnerAddJenisBarangMasuk.selectedItem as JenisBarang
                           for (jenisBarang in listJenisBarang) {
                               if (jenisBarang.jenisBarang == selectedJenisBarang.jenisBarang) {
                                   idJenisBarang = jenisBarang.id!!
                                   break
                               }

                           }
                           binding.spinnerAddJenisBarangMasuk.setSelection(
                               spinnerAdapter.getPosition(
                                   selectedJenisBarang
                               )
                           )
                       }

                       override fun onNothingSelected(parent: AdapterView<*>) {
                           // Do something when nothing is selected
                       }
                   }

           })

        val edtAddNamaBarangMasuk = binding.edtAddNamaBarangMasuk
        val edtAddJumlahBarangMasuk = binding.edtAddJumlahBarangMasuk
        val edtAddDeskripsiBarangMasuk = binding.edtAddDeskripsiBarangMasuk



        binding.btnAddBarangMasukToServer.setOnClickListener {
            when {
                edtAddDeskripsiBarangMasuk.text.toString()
                    .isEmpty() -> edtAddDeskripsiBarangMasuk.error = "Mohon isi bagian deskripsi"

                edtAddJumlahBarangMasuk.text.toString().isEmpty() -> edtAddJumlahBarangMasuk.error =
                    "Mohon isi berapa jumlah barang yang masuk"
                edtAddNamaBarangMasuk.text.toString().isEmpty() -> edtAddNamaBarangMasuk.error =
                    "Mohon isi bagian nama barang masuk"
                else -> {
                    postBarangMasukToServer(
                        namaBarangMasuk = edtAddNamaBarangMasuk.text.toString(),
                        jumlahBarangMasuk = edtAddJumlahBarangMasuk.text.toString(),
                        jenisBarangMasuk = idJenisBarang,
                        deskripsiBarangMasuk = edtAddDeskripsiBarangMasuk.text.toString(),
                        idSupplierBarangMasuk = idSupplier
                    )
                }
            }
        }
    }

    private fun postBarangMasukToServer(
        namaBarangMasuk: String,
        jumlahBarangMasuk: String,
        jenisBarangMasuk: String,
        deskripsiBarangMasuk: String,
        idSupplierBarangMasuk: String
    ) {
        binding.loadingAddBarangMasuk.visibility = View.VISIBLE
        binding.layoutAddBarangMasuk.visibility = View.INVISIBLE

        val getIdUser = loginSharedPref.getString("id", null)
        val api = RetrofitClient().retrofitApi
        api.inputBarangMasuk(
            namaBarang = namaBarangMasuk,
            jumlahBarang = jumlahBarangMasuk,
            deskripsiBarang = deskripsiBarangMasuk,
            id_user = getIdUser,
            jenisBarang = jenisBarangMasuk,
            idSupplier = idSupplierBarangMasuk
        ).enqueue(object : Callback<ResponseInputBarangMasuk> {
            override fun onResponse(
                call: Call<ResponseInputBarangMasuk>,
                response: Response<ResponseInputBarangMasuk>
            ) {
                if (response.isSuccessful) {
                    binding.loadingAddBarangMasuk.visibility = View.INVISIBLE
                    binding.layoutAddBarangMasuk.visibility = View.VISIBLE
                    Toast.makeText(
                        this@AddBarangMasukActivity,
                        "Barang masuk berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    binding.loadingAddBarangMasuk.visibility = View.INVISIBLE
                    binding.layoutAddBarangMasuk.visibility = View.VISIBLE
                    Toast.makeText(
                        this@AddBarangMasukActivity,
                        "Barang masuk gagal ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseInputBarangMasuk>, t: Throwable) {
                binding.loadingAddBarangMasuk.visibility = View.INVISIBLE
                binding.layoutAddBarangMasuk.visibility = View.VISIBLE
                Toast.makeText(
                    this@AddBarangMasukActivity,
                    "Barang masuk gagal ditambahkan,tolong periksa internetmu",
                    Toast.LENGTH_SHORT
                ).show()
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