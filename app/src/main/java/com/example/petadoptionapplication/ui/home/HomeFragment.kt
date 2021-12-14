package com.example.petadoptionapplication.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        val adapter = RecyclerAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        CoroutineScope(Dispatchers.IO).launch {
            val headerMap = mutableMapOf<String, String>()
            val sharedPreferences=activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

            headerMap["Authorization"]="Bearer "+sharedPreferences!!.getString("token",null)


            val decodedpets = petService.getPets(headerMap)
            withContext(Dispatchers.Main)
            {
                adapter.setData(decodedpets)
            }
        }
        return view
    }
}