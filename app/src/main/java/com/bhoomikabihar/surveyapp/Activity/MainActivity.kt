package com.bhoomikabihar.surveyapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    // var toolbar_pmkisanankshan: Toolbar? = null
    lateinit var toolbar_pmkisanankshan: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    var userRole = "AC"
    lateinit var userName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        var sessionManager = SessionManager(applicationContext)
        var user = sessionManager.fetchAuthACDetails()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.UserNameTitle).text = user.name.uppercase()
        headerView.findViewById<TextView>(R.id.textViewSubdtl).text = user.mobileNo

        userName = findViewById(R.id.userName)

        userName.setText(sessionManager.fetchAuthACDetails().name)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_Sync -> {
                if (userRole == "AC") {
                    val mainIntent = Intent(this, SyncApp::class.java)
                    startActivity(mainIntent)
                }

                true
            }
            R.id.action_profile -> {
//                val mainIntent = Intent(this, MyProfile::class.java)
//                startActivity(mainIntent)
                true
            }
            R.id.action_logout -> {
                var sessionManager: SessionManager = SessionManager(this)
                //val mainIntent = Intent(this, LoginActivity::class.java)
                sessionManager.clearToken()
                //startActivity(mainIntent)
                //finish()
                true
            }


            //=====Dayanand Logout Code=====

            /*R.id.action_logout -> {
                var sessionManager: SessionManager = SessionManager(this)

                val mainIntent = Intent(this, SplashScreen::class.java)
                var apiClient = ApiClient()
                //if (userRole == "AC") {
                apiClient.getApiService(applicationContext)
                    .acLogout()
                    .enqueue(object : Callback<Int> {
                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Something Wrong !",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Int>,
                            response: Response<Int>
                        ) {
                            if (response.isSuccessful) {
                                // if (response.body() == 1) {
                                sessionManager.clearToken()
                                startActivity(mainIntent)
                                finish()
                                // }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Something Wrong !",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                // }
                true
            }*/


            else -> super.onOptionsItemSelected(item)


        }
    }
}