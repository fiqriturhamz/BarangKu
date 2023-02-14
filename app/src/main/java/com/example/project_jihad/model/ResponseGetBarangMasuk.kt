package com.example.project_jihad.model

import com.google.gson.annotations.SerializedName

data class ResponseGetBarangMasuk(

	@field:SerializedName("data")
	val data: List<DataItemBarangMasuk?>? = null
)

data class DataItemBarangMasuk(

	@field:SerializedName("supplier")
	val supplier: String? = null,

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
