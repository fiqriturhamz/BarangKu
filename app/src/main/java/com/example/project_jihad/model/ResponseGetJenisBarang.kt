package com.example.project_jihad.model

import com.google.gson.annotations.SerializedName

data class ResponseGetJenisBarang(

	@field:SerializedName("data")
	val data: List<DataItemJenisBarang?>? = null
)

data class DataItemJenisBarang(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
