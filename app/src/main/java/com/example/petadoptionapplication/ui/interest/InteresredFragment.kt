package com.example.petadoptionapplication.ui.interest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.ui.home.RecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InteresredFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_interesred, container, false)

        val adapter = InterestAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.petInterestView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        CoroutineScope(Dispatchers.IO).launch {
            val decodedpetsInterests = petService.getPetInterests()
            val decodedpets = petService.getPets()

            withContext(Dispatchers.Main)
            {
                adapter.setData(decodedpets.pets,decodedpetsInterests.petInterests)
            }
        }

        return view
    }
}