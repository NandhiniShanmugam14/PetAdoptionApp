package com.example.petadoptionapplication.ui.others

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.others.usersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OthersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_others, container, false)

        val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val token=sharedPreferences!!.getString("token",null)
        val headerMap= mutableMapOf<String,String>()
        headerMap["Authorization"]= "Bearer " + token

        val adapter = OthersAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleViewLogin)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        CoroutineScope(Dispatchers.IO).launch {
            petService.getUsers(headerMap).enqueue(object : Callback<usersList?> {
                override fun onResponse(call: Call<usersList?>, response: Response<usersList?>) {
                    if(response.isSuccessful)
                    {
                        val decodedusers =response.body()!!
                        adapter.setData(decodedusers)
                    }
                    else
                    {
                        Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<usersList?>, t: Throwable) {
                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                }
            })

        }


        return view

    }
}