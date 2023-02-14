package com.example.project_jihad.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.project_jihad.databinding.ActivityRegisterBinding
import com.example.project_jihad.model.ResponseRegister
import com.example.project_jihad.network.RetrofitClient
import com.example.project_jihad.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser() {
        val edtRegisterFullname = binding.edtRegisterFullname
        val edtRegisterUsername = binding.edtRegisterUsername
        val edtRegisterPassword = binding.edtRegisterPassword
        val edtRegisterPhone = binding.edtRegisterPhoneNumber

        var isEmptyFields = false

        if (edtRegisterFullname.text.toString().isEmpty()) {
            isEmptyFields = true
            edtRegisterFullname.error = "Fullname harus diisi"

        }
        if (edtRegisterUsername.text.toString().isEmpty()) {
            isEmptyFields = true
            edtRegisterUsername.error =
                "Username harus diisi"
        }
        if(edtRegisterPassword.text.toString().isEmpty()) {
            isEmptyFields = true
            edtRegisterPassword.error =
                "Password harus diisi"
        }
        if (edtRegisterPhone.text.toString().isEmpty() ) {
            isEmptyFields = true
            edtRegisterPhone.error = "Phone number harus diisi"
        }
        if(!isEmptyFields)
         {
            sendRegisterUserToServer(edtRegisterFullname.text.toString(),edtRegisterUsername.text.toString(),edtRegisterPassword.text.toString(),edtRegisterPhone.text.toString())

        }
    }

    fun sendRegisterUserToServer(registerFullname : String,registerUsername : String,registerPassword : String,registerPhone : String){

        val api = RetrofitClient().retrofitApi
        api.registerUser(
            fullName = registerFullname,
            username = registerUsername,
            password = registerPassword,
            phoneNumber = registerPhone
        ).enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if(response.isSuccessful ){
                    val intent =Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@RegisterActivity,"Register berhasil silahkan login",Toast.LENGTH_SHORT).show()
                }else if(response.code() == 409){
                    Toast.makeText(this@RegisterActivity,"Register gagal,username sudah ada",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.e("gagal register",t.message.toString())
                Toast.makeText(this@RegisterActivity,"Register gagal cobalah periksa koneksi internetmu",Toast.LENGTH_SHORT).show()
            }


        })
    }

}

