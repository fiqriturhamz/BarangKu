package com.example.project_jihad.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project_jihad.SupplierData
import com.example.project_jihad.SupplierViewModel
import com.example.project_jihad.ui.barang_keluar.BarangKeluarActivity

import com.example.project_jihad.ui.barang.BarangActivity
import com.example.project_jihad.ui.barang_masuk.BarangMasukActivity
import com.example.project_jihad.databinding.ActivityMainBinding
import com.example.project_jihad.model.Supplier
import com.example.project_jihad.ui.login.LoginActivity
import com.example.project_jihad.ui.jenis_barang.JenisBarangActivity
import com.example.project_jihad.ui.supplier.SupplierActivity
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.content.DialogInterface
import android.net.Network
import android.util.Log
import com.example.project_jihad.JenisBarangViewModel
import com.example.project_jihad.model.JenisBarang


class MainActivity : AppCompatActivity() {
    private lateinit var loginSharedPref: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var supplierViewModel: SupplierViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supplierViewModel = ViewModelProvider(this).get(SupplierViewModel::class.java)
        binding.btnBarangMasuk.setOnClickListener {
            intentTo(BarangMasukActivity())
        }
        binding.btnImgSupplier.setOnClickListener {
            intentTo(SupplierActivity())
        }
        binding.btnJenisBarang.setOnClickListener {
            intentTo(JenisBarangActivity())
        }
        binding.btnBarangStok.setOnClickListener {
            intentTo(BarangActivity())
        }
        binding.btnBarangKeluar.setOnClickListener {
            intentTo(BarangKeluarActivity())
        }
        binding.user.text = loginSharedPref.getString("username", null)

        binding.btnLogout.setOnClickListener {
            loginSharedPref
                .edit()
                .clear()
                .commit()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            SupplierData.getInstance().clearData()
            finish()

        }


        loadingProgressBarMain(true)
        supplierViewModel.getSupplierFromServer(loginSharedPref.getString("id", null))
        supplierViewModel.response.observe(this, Observer {

            val data = it.body()?.data
            val listSupplier = ArrayList<Supplier>()
            for (i in data!!) {
                listSupplier.add(Supplier(i?.id, i?.namaSupplier))

            }
            val supplierData = SupplierData.getInstance()
            supplierData.setData(listSupplier)
        })

        supplierViewModel.loading.observe(this, {
            loadingProgressBarMain(it)
        })


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun intentTo(moveToNewActivity: Activity) {
        val intent = Intent(this@MainActivity, moveToNewActivity::class.java)
        startActivity(intent)
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

    private fun loadingProgressBarMain(loading: Boolean) {
        if (loading) {
            binding.loadingMain.visibility = View.VISIBLE
            binding.layoutMain.visibility = View.INVISIBLE

        } else {
            binding.loadingMain.visibility = View.INVISIBLE
            binding.layoutMain.visibility = View.VISIBLE


        }


    }
}