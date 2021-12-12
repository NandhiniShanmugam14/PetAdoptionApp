package com.example.petadoptionapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.navigation.NavigationView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPreferences=activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor= sharedPreferences!!.edit()

        view.findViewById<TextView>(R.id.logout).setOnClickListener()
        {
            editor.clear()
            editor.apply()
            val intent= Intent(activity,LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return view
    }
}