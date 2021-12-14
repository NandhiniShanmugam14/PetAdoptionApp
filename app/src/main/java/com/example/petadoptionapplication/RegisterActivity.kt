package com.example.petadoptionapplication

import android.app.ProgressDialog
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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        findViewById<TextView>(R.id.regButton).setOnClickListener()
        {
            val mail=findViewById<TextInputLayout>(R.id.regusermail).editText?.text
            val password=findViewById<TextInputLayout>(R.id.reguserpassword).editText?.text

            val petapplication=application as PetApplication
            val petservice=petapplication.api

            val user= UserReq(mail.toString(),password.toString())
            CoroutineScope(Dispatchers.IO).launch {

                petservice.userRegister(user).enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    )
                    {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_SHORT).show()

                            petservice.userLogin(user).enqueue(object : Callback<User?> {
                                override fun onResponse(
                                    call: Call<User?>,
                                    response: Response<User?>
                                ) {
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

                                    val progressDialog=ProgressDialog(this@RegisterActivity,R.style.PetAppDialogStyle)
                                    progressDialog.setTitle("Logging in")
                                    progressDialog.setMessage("Loading")
                                    progressDialog.show()

                                    val intent=Intent(this@RegisterActivity,HomeNavActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                override fun onFailure(call: Call<User?>, t: Throwable) {
                                    Toast.makeText(this@RegisterActivity,"Problem in Login",Toast.LENGTH_LONG).show()
                                    val intent=Intent(this@RegisterActivity,LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            })
                        }
                        else
                        {
                            Toast.makeText(this@RegisterActivity,"Email id already Taken",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        findViewById<TextView>(R.id.regerrorMsg).text="Registration failed"
                    }
                })


                }
            }
        }
    }
