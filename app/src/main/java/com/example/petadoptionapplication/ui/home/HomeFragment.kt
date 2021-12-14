package com.example.petadoptionapplication.ui.home

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
import com.example.petadoptionapplication.data.pets.PetList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        val adapter = RecyclerAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleViewLogin)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        CoroutineScope(Dispatchers.IO).launch {
            petService.getPets().enqueue(object : Callback<PetList?> {
                override fun onResponse(call: Call<PetList?>, response: Response<PetList?>) {
                    if (response.isSuccessful)
                    {
                        val decodedpets = response.body()!!.pets
                        if (decodedpets.isEmpty())
                        {
                            Toast.makeText(context,"Nothing yet", Toast.LENGTH_LONG).show()
                        }
                        else
                        {
                            adapter.setData(decodedpets)
                        }
                    }
                }

                override fun onFailure(call: Call<PetList?>, t: Throwable) {
                    Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        return view
    }
}