package com.example.project_jihad.network

import android.app.Activity
import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.example.project_jihad.ResponseGetBarangKeluar
import com.example.project_jihad.model.ResponseInputJenisBarang
import com.example.project_jihad.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("get_user.php")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("post_user.php")
    fun registerUser(
        @Field("full_name") fullName: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone_number") phoneNumber: String
    ): Call<ResponseRegister>

    @POST("update_user.php")
    fun updateUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("new_password") newPassword: String,
        @Field("full_name") fullname: String,
        @Field("phone_number") phoneNumber: String
    )

    @FormUrlEncoded
    @POST("update_password.php")
    fun updatePassword(
        @Field("username") username: String,
        @Field("new_password") newPassword: String
    ): Call<ResponseUpdatePassword>

    @FormUrlEncoded
    @POST("post_supplier.php")
    fun inputSupplier(
        @Field("supplier_name") supplierName: String,
        @Field("company_name") companyName: String,
        @Field("id_user") idUser: String?
    ): Call<ResponseInputSupplier>

    @GET("get_data_supplier.php")
    fun getDataSupplier(@Query("id_user") idUser: String?): Call<ResponseGetSupplier>


    @FormUrlEncoded
    @POST("post_jenis_barang_baru.php")
    fun inputJenisBarang(
        @Field("jenis_barang") jenisBarang: String,
        @Field("deskripsi") deskripsiBarang: String,
        @Field("id_user") idUser: String?
    ): Call<ResponseInputJenisBarang>

    @GET("get_jenis_barang.php")
    fun getAllJenisBarang(@Query("id_user") idUser: String?): Call<ResponseGetJenisBarang>

    @FormUrlEncoded
    @POST("input_barang_masuk.php")
    fun inputBarangMasuk(
        @Field("nama_barang") namaBarang: String,
        @Field("jumlah_barang") jumlahBarang: String,
        @Field("deskripsi") deskripsiBarang: String,
        @Field("id_user") id_user: String?,
        @Field("jenis_barang") jenisBarang: String,
        @Field("id_supplier") idSupplier: String?
    ): Call<ResponseInputBarangMasuk>

    @GET("get_data_barang.php")
    fun getBarang(@Query("id_user") idUser: String?): Call<ResponseGetBarang>

    @FormUrlEncoded
    @POST("input_barang_keluar.php")
    fun inputBarangKeluar(
        @Field("id_barang") idBarang: String,
        @Field("jumlah_barang") jumlahBarang: String,
        @Field("deskripsi") deskripsi: String,
        @Field("id_user") idUser: String?
    ): Call<ResponseInputBarangKeluar>

    @GET("get_data_barang_keluar.php")
    fun getBarangKeluar(@Query("id_user") idUser: String?): Call<ResponseGetBarangKeluar>

    @GET("get_data_barang_masuk.php")
    fun getBarangMasuk(@Query("id_user") idUser: String?): Call<ResponseGetBarangMasuk>

}