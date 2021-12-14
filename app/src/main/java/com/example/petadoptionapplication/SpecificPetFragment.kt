package com.example.petadoptionapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

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

        if(arguments?.getInt("age",0)!!.toInt()>1)
        {
            view.findViewById<Button>(R.id.interest_button).text="I am interested in"
        }
        return view
    }
}