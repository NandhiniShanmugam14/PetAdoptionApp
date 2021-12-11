package com.example.petadoptionapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mail=findViewById<TextInputLayout>(R.id.usermail).editText?.text
        val password=findViewById<TextInputLayout>(R.id.userpassword).editText?.text

        findViewById<TextView>(R.id.registerButton).setOnClickListener()
        {
            val intent=Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.loginButton).setOnClickListener()
        {
            val petapplication=application as PetApplication
            val petservice=petapplication.api
            val user=UserReq(mail.toString(),password.toString())
            CoroutineScope(Dispatchers.IO).launch {

                petservice.userLogin(user).enqueue(object : Callback<User?> {
                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                        if(response.isSuccessful)
                        {
                            val userValue=response.body()!!
                            val sharedPreferences=getSharedPreferences("user",Context.MODE_PRIVATE)
                            val editor=sharedPreferences.edit()
                            editor.apply()
                            {
                                putInt("id",userValue.id)
                                putString("token",userValue.token)
                                putLong("memberSince",userValue.memberSince)
                                putString("mail",userValue.email)
                            }.apply()

                            val intent=Intent(this@LoginActivity,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            findViewById<TextView>(R.id.errorMsg).text="Invalid User or Password"
                        }
                    }

                    override fun onFailure(call: Call<User?>, t: Throwable) {
                        findViewById<TextView>(R.id.errorMsg).text="Invalid User or Password"
                    }
                })
            }
        }
    }
}