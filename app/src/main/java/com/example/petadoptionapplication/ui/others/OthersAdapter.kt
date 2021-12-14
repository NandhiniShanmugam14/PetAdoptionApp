package com.example.petadoptionapplication.ui.others

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.LoginHistory.loginEntriesList
import com.example.petadoptionapplication.data.LoginHistory.loginTimestamp
import com.example.petadoptionapplication.data.others.others
import com.example.petadoptionapplication.data.others.usersList
import com.example.petadoptionapplication.ui.loginhistory.LoginAdapter
import java.time.format.DateTimeFormatter

class OthersAdapter : RecyclerView.Adapter<OthersAdapter.MyViewHolder>() {

    var usersList = emptyList<others>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OthersAdapter.MyViewHolder {
        return OthersAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.others_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OthersAdapter.MyViewHolder, position: Int) {
        val currentItem = usersList[position]
        holder.itemView.findViewById<TextView>(R.id.email).text= currentItem.email
        holder.itemView.findViewById<TextView>(R.id.reservationsAt).text="Interested in: "+ currentItem.reservationsAt
        holder.itemView.findViewById<TextView>(R.id.textView).text= currentItem.email.substring(0, 1).uppercase()

    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun setData(others: usersList)
    {
        this.usersList=others.users
        notifyDataSetChanged()
    }


}