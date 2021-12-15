package com.example.petadoptionapplication

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.Navigation
import com.example.petadoptionapplication.data.PetApplication
import com.example.petadoptionapplication.data.pets.PetInterests
import com.example.petadoptionapplication.data.pets.SendPet
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificPetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_specific_pet, container, false)
        view.findViewById<TextView>(R.id.get_name).text="Name: "+arguments?.getString("name",null)
        view.findViewById<TextView>(R.id.get_type).text="Type:"+arguments?.getString("type",null)
        if(arguments?.getInt("age",0)!! >1)
        {
            view.findViewById<TextView>(R.id.get_age).text="Age: "+arguments?.getInt("age",0)+"years"
        }
        else if(arguments?.getInt("age",0)==1)
        {
            view.findViewById<TextView>(R.id.get_age).text="Age: "+arguments?.getInt("age",0)+"year"
        }
        if(arguments?.getString("vaccinated",null)!!.toInt()>=1)
        {
            view.findViewById<TextView>(R.id.get_vaccine).text="Vaccinated: Yes"
        }
        else
        {
            view.findViewById<TextView>(R.id.get_vaccine).text="Vaccinated: No"
        }
        val image=view.findViewById<ImageView>(R.id.get_image)
        Picasso.get().load(arguments?.getString("url",null)).into(image)

        CoroutineScope(Dispatchers.IO).launch {
            val petapplication=activity?.application as PetApplication
            val petService=petapplication.api

            val petId:Int= arguments?.getInt("id",0)!!

            val sharedPreferences=activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
            val token=sharedPreferences!!.getString("token",null)
            val headerMap= mutableMapOf<String,String>()
            headerMap["Authorization"]= "Bearer " + token

            petService.getPetInterests(headerMap).enqueue(object : Callback<PetInterests?> {
                override fun onResponse(
                    call: Call<PetInterests?>,
                    response: Response<PetInterests?>
                ) {
                    if(response.isSuccessful)
                    {
                        val pets=response.body()!!.petInterests
                        if(pets.isEmpty())
                        {
                            view.findViewById<Button>(R.id.interest_button).text="I am interested in"
                        }
                        for(item in pets) {
                            if (item.petId == petId) {
                                view.findViewById<Button>(R.id.interest_button).isEnabled = false
                                view.findViewById<Button>(R.id.interest_button).text = "Already Added"
                                break
                            }
                            else
                            {
                                view.findViewById<Button>(R.id.interest_button).text="I am interested in"
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<PetInterests?>, t: Throwable) {
                    Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
                }
            })

            view.findViewById<Button>(R.id.interest_button).setOnClickListener()
            {
                var sendPet=SendPet(petId)
                petService.postInterest(sendPet,headerMap).enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if(response.isSuccessful)
                        {
                            Toast.makeText(context,"Added Successfully!!", Toast.LENGTH_LONG).show()
                            val progressDialog= ProgressDialog(context,R.style.PetAppDialogStyle)
                            progressDialog.setTitle("Adding Pet Interest")
                            progressDialog.setMessage("Loading")
                            progressDialog.show()
                            activity!!.recreate()
                        }
                        else
                        {
                            Toast.makeText(context,"Something went wrong!!", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
            }

        return view
    }
}