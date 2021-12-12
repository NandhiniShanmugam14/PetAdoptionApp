package com.example.petadoptionapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.petadoptionapplication.data.ApiInterface
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.user.User
import com.example.petadoptionapplication.data.user.UserReq
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mail=findViewById<TextInputLayout>(R.id.regusermail).editText?.text
        val password=findViewById<TextInputLayout>(R.id.reguserpassword).editText?.text

        findViewById<TextView>(R.id.regButton).setOnClickListener()
        {
            val petapplication=application as PetApplication
            val petservice=petapplication.api

            val user= UserReq(mail.toString(),password.toString())
            CoroutineScope(Dispatchers.IO).launch {

                petservice.userRegister(user).enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        if(response.isSuccessful)
                        {

                            Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_SHORT)
                                .show()

                            val intent=Intent(this@RegisterActivity,LoginActivity::class.java)
                            startActivity(intent)

                        }
                        else
                        {

                            findViewById<TextView>(R.id.regerrorMsg).text="Credentials are wrong"
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        findViewById<TextView>(R.id.regerrorMsg).text="Registration failed"
                    }
                })
            }
        }
    }
}