package com.example.project_jihad.model

import com.google.gson.annotations.SerializedName

data class ResponseGetBarang(

	@field:SerializedName("data")
	val data: List<DataItemBarang?>? = null
)

data class DataItemBarang(

	@field:SerializedName("qty")
	val qty: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
