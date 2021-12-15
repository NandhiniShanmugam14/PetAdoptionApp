package com.example.petadoptionapplication.ui.changemail

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.user.ChangeEmail
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeMailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_change_mail, container, false)

        view.findViewById<TextView>(R.id.confirmchange).setOnClickListener()
        {
            val changeMail= view.findViewById<TextInputLayout>(R.id.changeEmail).editText?.text
            val mail=ChangeEmail(changeMail.toString())

            val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
            val token=sharedPreferences!!.getString("token",null)
            val headerMap= mutableMapOf<String,String>()
            headerMap["Authorization"]= "Bearer " + token

            CoroutineScope(Dispatchers.IO).launch {

                val petApplication= activity?.application as PetApplication
                val petService=petApplication.api
                petService.changeMail(mail,headerMap).enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(context,"Changed Mail Successfully!!",Toast.LENGTH_LONG).show()
                            val editor=sharedPreferences.edit()
                            editor.apply()
                            {
                                putString("mail",changeMail.toString())
                            }.apply()
                            Navigation.findNavController(view).navigate(R.id.nav_home)
                            val progressDialog= ProgressDialog(view.context,R.style.PetAppDialogStyle)
                            progressDialog.setTitle("Logging in")
                            progressDialog.setMessage("Loading")
                            progressDialog.show()
                            activity?.recreate()
                        }
                        else
                        {
                            Toast.makeText(context,"Something Went Wrong!!",Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        return view
    }
}