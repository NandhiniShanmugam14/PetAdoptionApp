package com.example.petadoptionapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
        holder.itemView.findViewById<ConstraintLayout>(R.id.relativeLayout).setOnClickListener()
        {
            val bundle=Bundle()
            bundle.putInt("id",currentItem.id)
            bundle.putString("name",currentItem.name)
            bundle.putString("type",currentItem.type)
            bundle.putString("url",currentItem.url)
            bundle.putString("vaccinated",currentItem.vaccinated)
            bundle.putInt("age",currentItem.age)
            Navigation.findNavController(holder.itemView).navigate(R.id.nav_specific,bundle)
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