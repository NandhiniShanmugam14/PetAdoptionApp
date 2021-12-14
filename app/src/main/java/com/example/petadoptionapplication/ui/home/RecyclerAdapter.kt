package com.example.petadoptionapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.pets.PetList
import com.example.petadoptionapplication.data.pets.Pets
import com.squareup.picasso.Picasso

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    var petList = emptyList<Pets>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = petList[position]
        holder.itemView.findViewById<TextView>(R.id.pet_name).text= currentItem.name
        holder.itemView.findViewById<TextView>(R.id.pet_type).text=currentItem.type
        holder.itemView.findViewById<TextView>(R.id.pet_age).text= currentItem.age.toString()
        val imageview= holder.itemView.findViewById<ImageView>(R.id.pet_image)
        Picasso.get().load(currentItem.url).into(imageview)
        if (currentItem.age <= 1)
        {
            holder.itemView.findViewById<TextView>(R.id.year_txt).text = "year"
        }
        else
        {
            holder.itemView.findViewById<TextView>(R.id.year_txt).text = "years"
        }
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    fun setData(pets: PetList)
    {
        this.petList=pets.pets
        notifyDataSetChanged()
    }
}