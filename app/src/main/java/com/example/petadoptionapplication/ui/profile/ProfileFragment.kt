package com.example.petadoptionapplication.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.petadoptionapplication.LoginActivity
import com.example.petadoptionapplication.R
import okhttp3.Cache
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment() {
    val reqCode=100
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPreferences= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val email=sharedPreferences!!.getString("mail",null)
        val token=sharedPreferences!!.getString("token",null)
        val memberSince=sharedPreferences.getLong("memberSince",0)
        val instant= java.time.Instant.ofEpochMilli(memberSince).atZone(ZoneId.of("US/Pacific"))
            .toLocalDateTime()
        view.findViewById<TextView>(R.id.membersince).text="Member since: "+DateTimeFormatter.ofPattern("dd MMM yyyy").format(instant)
        view.findViewById<TextView>(R.id.mailid).text=email
        view.findViewById<TextView>(R.id.imagePicText).text= sharedPreferences.getString("mail", null)?.substring(0,1)?.uppercase()
        val editor= sharedPreferences.edit()

        view.findViewById<TextView>(R.id.logout).setOnClickListener()
        {
            editor.clear()
            editor.apply()
            val intent= Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        view.findViewById<Button>(R.id.changEmail).setOnClickListener()
        {
            Navigation.findNavController(view).navigate(R.id.nav_change_mail)
        }

        view.findViewById<Button>(R.id.reviewbutton).setOnClickListener()
        {
            Navigation.findNavController(view).navigate(R.id.nav_login_history)
        }

        view.findViewById<Button>(R.id.deletebutton).setOnClickListener()
        {
            Navigation.findNavController(view).navigate(R.id.nav_delete)
        }

        view.findViewById<TextView>(R.id.add).setOnClickListener()
        {

            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,100)

        }
        val pic=activity?.getSharedPreferences("photos",Context.MODE_PRIVATE)?.getString(email,null)
        if(pic!=null) {
            val decoded = Base64.decode(pic, 0)
            val image = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
            view.findViewById<ImageView>(R.id.picCapture)?.setImageBitmap(image)
            view.findViewById<TextView>(R.id.imagePicText)?.text = ""
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==reqCode&&data!=null)
        {
            val bitmap=data.extras!!.get("data") as Bitmap
            val byte=ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byte)
            val byteArray=byte.toByteArray()
            val encode=Base64.encodeToString(byteArray,Base64.DEFAULT)

            val sharedPreferences1= activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
            val token=sharedPreferences1!!.getString("token",null)
            val mail=sharedPreferences1!!.getString("mail",null)
            val sharedPreferences=activity?.getSharedPreferences("photos",Context.MODE_PRIVATE)

            val editor=sharedPreferences!!.edit()
            editor.apply()
            {
                putString("token",token)
                putString(mail,encode)
            }.commit()
            activity?.recreate()
        }
    }
}