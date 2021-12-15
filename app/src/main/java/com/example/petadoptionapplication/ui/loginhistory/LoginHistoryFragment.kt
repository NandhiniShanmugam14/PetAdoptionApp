package com.example.petadoptionapplication.ui.loginhistory
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petadoptionapplication.R
import com.example.petadoptionapplication.data.LoginHistory.loginEntriesList
import com.example.petadoptionapplication.data.PetApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LoginHistoryFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_login_history, container, false)

        val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val token=sharedPreferences!!.getString("token",null)
        val headerMap= mutableMapOf<String,String>()
        headerMap["Authorization"]= "Bearer " + token

        val memberSince= sharedPreferences!!.getLong("memberSince",0)
        val instant= java.time.Instant.ofEpochMilli(memberSince).atZone(ZoneId.of("US/Pacific"))
            .toLocalDateTime()
        view.findViewById<TextView>(R.id.memberSince).text="Member since: "+ DateTimeFormatter.ofPattern("dd MMM yyyy").format(instant)

        val adapter = LoginAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleViewLogin)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val petApplication = activity?.application as PetApplication
        val petService = petApplication.api

        CoroutineScope(Dispatchers.IO).launch {
            petService.getLoginEntries(headerMap).enqueue(object : Callback<loginEntriesList?> {
                override fun onResponse(
                    call: Call<loginEntriesList?>,
                    response: Response<loginEntriesList?>
                ) {
                    if(response.isSuccessful)
                    {
                        val decodedLogHistory=response.body()!!
                        adapter.setData(decodedLogHistory)
                    }
                    else
                    {
                        Toast.makeText(context,"Something Went Wrong!!",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<loginEntriesList?>, t: Throwable) {
                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                }
            })
        }


        return view
    }
}