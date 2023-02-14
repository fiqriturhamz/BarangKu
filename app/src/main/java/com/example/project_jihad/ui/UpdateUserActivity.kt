package com.example.project_jihad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.project_jihad.databinding.ActivityUpdateUserBinding
import com.example.project_jihad.model.ResponseUpdatePassword
import com.example.project_jihad.network.RetrofitClient
import com.example.project_jihad.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEdit.setOnClickListener {
            editUser()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun editUser() {
        val edtUpdateUsername = binding.edtUpdateUsername
        val edtUpdateNewPassword = binding.edtUpdateNewPassword

        when {
            edtUpdateUsername.text.toString().isEmpty() -> edtUpdateUsername.error =
                "Silahkan masukkan username anda"
            edtUpdateNewPassword.text.toString().isEmpty() -> edtUpdateNewPassword.error =
                "Silahkan masukkan password baru anda"
            else ->sendUpdateUserToServer(username =  edtUpdateUsername.text.toString(), newPassword =  edtUpdateNewPassword.text.toString())
        }
    }

    private fun sendUpdateUserToServer(username: String, newPassword: String) {
        val api = RetrofitClient().retrofitApi
        api.updatePassword(username = username, newPassword = newPassword).enqueue(object :
            Callback<ResponseUpdatePassword> {
            override fun onResponse(
                call: Call<ResponseUpdatePassword>,
                response: Response<ResponseUpdatePassword>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(this@UpdateUserActivity,"Password berhasil diubah",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UpdateUserActivity, LoginActivity::class.java)
                    startActivity(intent)
                }else if(response.code() == 409){
                    Toast.makeText(this@UpdateUserActivity,"Gagal update password,Username tidak ada",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseUpdatePassword>, t: Throwable) {
                Log.e("gagal update password",t.message.toString())
                Toast.makeText(this@UpdateUserActivity,"Update password gagal cobalah periksa koneksi internetmu",Toast.LENGTH_SHORT).show()
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