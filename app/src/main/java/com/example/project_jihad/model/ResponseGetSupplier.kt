package com.example.project_jihad.model

import com.google.gson.annotations.SerializedName

data class ResponseGetSupplier(

	@field:SerializedName("data")
	val data: List<DataItemGetSupplier?>
)

data class DataItemGetSupplier(

	@field:SerializedName("nama_supplier")
	val namaSupplier: String? = null,

	@field:SerializedName("brand_company")
	val brandCompany: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
