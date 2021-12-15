package com.example.petadoptionapplication.ui.interest

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.pets.PetInterestList
import com.example.petadoptionapplication.data.pets.PetInterests
import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.data.pets.Pets
import com.example.petadoptionapplication.ui.home.RecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InteresredFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_interesred, container, false)
        val application=activity?.application
        val adapter = InterestAdapter(application!!)
        val recyclerView = view.findViewById<RecyclerView>(R.id.petInterestView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        val sharedPreferences=activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token=sharedPreferences!!.getString("token",null)
        val headerMap= mutableMapOf<String,String>()
        headerMap["Authorization"]= "Bearer " + token

        CoroutineScope(Dispatchers.IO).launch {
            petService.getPetInterests(headerMap).enqueue(object : Callback<PetInterests?> {
                override fun onResponse(
                    call: Call<PetInterests?>,
                    response: Response<PetInterests?>
                ) {
                    if(response.isSuccessful)
                    {
                        var decodedpetsInterests= response.body()!!.petInterests
                        petService.getPets(headerMap).enqueue(object : Callback<PetList?> {
                            override fun onResponse(call: Call<PetList?>, response: Response<PetList?>) {
                                if(response.isSuccessful)
                                {
                                    var decodedpets=response.body()!!.pets
                                    if (decodedpetsInterests.isEmpty())
                                    {
                                        view.findViewById<TextView>(R.id.emptyMsg).text="You are not interested\n           in a pet yet"
                                    }
                                    else
                                    {
                                        adapter.setData(decodedpets,decodedpetsInterests)
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<PetList?>, t: Throwable) {
                                Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<PetInterests?>, t: Throwable) {
                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                }
            })
        }

        return view
    }
}