package com.example.petadoptionapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.petadoptionapplication.databinding.ActivityHomeNavBinding

class HomeNavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeNav.toolbar)
        val navigationView : NavigationView = findViewById(R.id.nav_view)
        val headerView : View = navigationView.getHeaderView(0)
        val navUserEmail : TextView = headerView.findViewById(R.id.nav_mail)
        val logoLetter : TextView = headerView.findViewById(R.id.LogoText)
        val sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)
        navUserEmail.text=sharedPreferences.getString("mail",null)
        logoLetter.text = sharedPreferences.getString("mail", null)?.substring(0,1)?.uppercase()

        val token=sharedPreferences.getString("token",null)

//        val pic=getSharedPreferences("photos",Context.MODE_PRIVATE)?.getString("photo",null)
//        val getToken=getSharedPreferences("photos",Context.MODE_PRIVATE)?.getString("token",null)
//        if(pic!=null && token==getToken) {
//            val decoded = Base64.decode(pic,0)
//            val image = BitmapFactory.decodeByteArray(decoded,0,decoded.size)
//            findViewById<ImageView>(R.id.LogoNav).setImageBitmap(image)
//            findViewById<TextView>(R.id.LogoText).text = ""
//        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home_nav)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_profile,R.id.nav_others,R.id.nav_interested
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}