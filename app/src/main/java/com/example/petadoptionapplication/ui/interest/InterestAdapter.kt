package com.example.petadoptionapplication.ui.interest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.pets.PetInterestList
import com.example.petadoptionapplication.data.pets.Pets
import com.squareup.picasso.Picasso

class InterestAdapter: RecyclerView.Adapter<InterestAdapter.MyViewHolder>() {
    var newpetInterests = emptyList<PetInterestList>()
    var newpets= emptyList<Pets>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.interest_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newpets[position]
        for(item in newpetInterests) {

            if(item.petId==currentItem.id) {
                holder.itemView.findViewById<TextView>(R.id.petint_name).text = currentItem.name
                holder.itemView.findViewById<TextView>(R.id.petint_type).text =
                    currentItem.type
        holder.itemView.findViewById<TextView>(R.id.petint_age).text= currentItem.age.toString()
                val imageview = holder.itemView.findViewById<ImageView>(R.id.petint_image)
                Picasso.get().load(currentItem.url).into(imageview)
                if (currentItem.age <= 1)
                {
                    holder.itemView.findViewById<TextView>(R.id.yearint_txt).text = "year"
                }
                else
                {
                    holder.itemView.findViewById<TextView>(R.id.yearint_txt).text = "years"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newpetInterests.size
    }

    fun setData(pets: List<Pets>, _newpets: List<PetInterestList>)
    {
        this.newpets=pets
        this.newpetInterests=_newpets
        notifyDataSetChanged()
    }
}