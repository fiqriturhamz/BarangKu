package com.example.project_jihad.ui.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.project_jihad.databinding.ActivityLoginBinding
import com.example.project_jihad.model.ResponseLogin
import com.example.project_jihad.network.RetrofitClient
import com.example.project_jihad.ui.register.RegisterActivity
import com.example.project_jihad.ui.UpdateUserActivity
import com.example.project_jihad.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginSharedPref : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveLoginSession()
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.tvEdit.setOnClickListener {
            val intent = Intent(this, UpdateUserActivity::class.java)
            startActivity(intent)
        }


    }

    private fun login() {
        var edtUsername = ""
        var edtPassword = ""
        edtUsername = binding.edtUsername.text.toString()
        edtPassword = binding.edtPassword.text.toString()

        when {
            edtUsername == "" -> binding.edtUsername.error = "Masukkan username anda"
            edtPassword == "" -> binding.edtPassword.error = "Masukkan password anda"
            else -> {
                    getLogin(edtUsername, edtPassword)
            }
        }
    }

       fun getLogin(username: String, password: String) {
           val api = RetrofitClient().retrofitApi
           api.loginUser(username, password)
               .enqueue(object : Callback<ResponseLogin> {
                   override fun onResponse(
                       call: Call<ResponseLogin>,
                       response: Response<ResponseLogin>
                   ) {
                       if (response.isSuccessful) {
                           getSharedPreferences("login_session", MODE_PRIVATE)
                               .edit()
                               .putString("id",response.body()?.data?.id)
                               .putString("username",response.body()?.data?.username)
                               .putString("password",response.body()?.data?.password)
                               .putString("fullname",response.body()?.data?.fullName)
                               .putString("phone",response.body()?.data?.phoneNumber)
                               .apply()
                           val intent =Intent(this@LoginActivity, MainActivity::class.java)
                           startActivity(intent)

                           Toast.makeText(this@LoginActivity,"Login sukses",Toast.LENGTH_SHORT).show()
                           finish()

                       } else if (response.code() == 404) {
                           Toast.makeText(this@LoginActivity,"Username atau password anda salah",Toast.LENGTH_SHORT).show()
                       }

                   }

                   override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                       Toast.makeText(this@LoginActivity,"Login gagal cobalah periksa koneksi internetmu",Toast.LENGTH_SHORT).show()
                   }
               })
       }
    private fun saveLoginSession (){
        loginSharedPref = getSharedPreferences("login_session", MODE_PRIVATE)
        if(loginSharedPref.getString("username",null) != null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}