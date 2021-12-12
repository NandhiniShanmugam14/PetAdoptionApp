package com.example.petadoptionapplication

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.petadoptionapplication.R
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ProfileFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val email=sharedPreferences!!.getString("mail",null)
        val memberSince=sharedPreferences.getLong("memberSince",0)
        val instant= java.time.Instant.ofEpochMilli(memberSince).atZone(ZoneId.of("US/Pacific"))
            .toLocalDateTime()
        view.findViewById<TextView>(R.id.membersince).text="Member since: "+DateTimeFormatter.ofPattern("dd MMM yyyy").format(instant)
        view.findViewById<TextView>(R.id.mailid).text=email

        return view
    }
}