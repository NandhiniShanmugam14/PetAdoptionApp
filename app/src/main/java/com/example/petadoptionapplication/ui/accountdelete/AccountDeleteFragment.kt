package com.example.petadoptionapplication.ui.accountdelete

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
import com.example.petadoptionapplication.LoginActivity
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountDeleteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_account_delete, container, false)
        val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val token=sharedPreferences!!.getString("token",null)
        val headerMap= mutableMapOf<String,String>()
        headerMap["Authorization"]= "Bearer " + token

        view.findViewById<TextView>(R.id.emailid).text=sharedPreferences.getString("mail",null)
        val petApplication=activity?.application as PetApplication
        val petService=petApplication.api

        view.findViewById<Button>(R.id.confirm).setOnClickListener()
        {
            CoroutineScope(Dispatchers.IO).launch {

                petService.deleteUser(headerMap).enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Deleted User", Toast.LENGTH_LONG).show()
                            val progressDialog= ProgressDialog(context,R.style.PetAppDialogStyle)
                            progressDialog.setTitle("User Deleting")
                            progressDialog.setMessage("Loading")
                            progressDialog.show()
                            val intent = Intent(context, LoginActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(context, "Deleted User UnSuccessful", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        return view
    }

}