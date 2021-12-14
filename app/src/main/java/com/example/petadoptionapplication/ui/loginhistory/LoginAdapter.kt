package com.example.petadoptionapplication.ui.loginhistory

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.LoginHistory.loginEntriesList
import com.example.petadoptionapplication.data.LoginHistory.loginTimestamp
import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.ui.home.RecyclerAdapter
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class LoginAdapter : RecyclerView.Adapter<LoginAdapter.MyViewHolder>() {
    var loginEntriesList = emptyList<loginTimestamp>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginAdapter.MyViewHolder {
        return LoginAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.login_layout, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: LoginAdapter.MyViewHolder, position: Int) {
        val currentItem = loginEntriesList[position]
        val instant= java.time.Instant.ofEpochMilli(currentItem.loginTimestamp).atZone(ZoneId.of("US/Pacific"))
            .toLocalDateTime()
        holder.itemView.findViewById<TextView>(R.id.logged_in).text="Logged in: "+ DateTimeFormatter.ofPattern("dd MMM yyyy").format(instant)

    }

    override fun getItemCount(): Int {
        return loginEntriesList.size
    }

    fun setData(loginTimestamp: loginEntriesList)
    {
        this.loginEntriesList=loginTimestamp.loginEntries
        notifyDataSetChanged()
    }




}