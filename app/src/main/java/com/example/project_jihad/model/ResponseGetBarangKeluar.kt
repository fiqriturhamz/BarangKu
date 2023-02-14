package com.example.project_jihad

import com.google.gson.annotations.SerializedName

data class ResponseGetBarangKeluar(

	@field:SerializedName("data")
	val data: ArrayList<DataItemBarangKeluar?>? = null
)

data class DataItemBarangKeluar(

	@field:SerializedName("tgl")
	val tgl: String? = null,

	@field:SerializedName("jumlah_barang")
	val jumlahBarang: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
