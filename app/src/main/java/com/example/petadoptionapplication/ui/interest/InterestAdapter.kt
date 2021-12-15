package com.example.petadoptionapplication.ui.interest

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.pets.PetInterestList
import com.example.petadoptionapplication.data.pets.Pets
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestAdapter(private val application: Application): RecyclerView.Adapter<InterestAdapter.MyViewHolder>() {
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
                holder.itemView.findViewById<TextView>(R.id.petint_type).text = currentItem.type
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
                holder.itemView.findViewById<TextView>(R.id.notInterestButton).setOnClickListener()
                {
                    val petApplication=application as PetApplication
                    val petService= petApplication.api

                    val sharedPreferences= holder.itemView.context.getSharedPreferences("user", Context.MODE_PRIVATE)
                    val token=sharedPreferences!!.getString("token",null)
                    val headerMap= mutableMapOf<String,String>()
                    headerMap["Authorization"]= "Bearer " + token


                    petService.deleteInterest(item.interestId,headerMap).enqueue(object : Callback<ResponseBody?> {
                        override fun onResponse(
                            call: Call<ResponseBody?>,
                            response: Response<ResponseBody?>
                        ) {
                            if(response.isSuccessful)
                            {
                                Toast.makeText(holder.itemView.context,"Deleted Interest",Toast.LENGTH_LONG).show()
                                val progressDialog= ProgressDialog(holder.itemView.context,R.style.PetAppDialogStyle)
                                progressDialog.setTitle("Logging in")
                                progressDialog.setMessage("Loading")
                                progressDialog.show()
                            }
                            else
                            {
                                Toast.makeText(holder.itemView.context,"Something went Wrong!!",Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                            Toast.makeText(holder.itemView.context,t.message,Toast.LENGTH_LONG).show()
                        }
                    })
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