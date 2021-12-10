package com.example.petadoptionapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.user.User
import com.example.petadoptionapplication.data.user.UserReq
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

        val mail=findViewById<EditText>(R.id.usermail).text
        val password=findViewById<EditText>(R.id.userpassword).text

        findViewById<Button>(R.id.button).setOnClickListener()
        {
            val petapplication=application as PetApplication
            val petservice=petapplication.api
            val user=UserReq(mail.toString(),password.toString())
            CoroutineScope(Dispatchers.IO).launch {

                petservice.userLogin(user).enqueue(object : Callback<User?> {
                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                        if(response.isSuccessful)
                        {
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