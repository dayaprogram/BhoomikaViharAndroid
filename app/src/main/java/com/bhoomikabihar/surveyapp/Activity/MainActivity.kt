package com.bhoomikabihar.surveyapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback.AboutHeSheActivity
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var userRole = "AC"
    lateinit var userName: TextView
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        var sessionManager = SessionManager(applicationContext)
        var user = sessionManager.fetchAuthACDetails()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.UserNameTitle).text = user.name.uppercase()
        headerView.findViewById<TextView>(R.id.textViewSubdtl).text = user.mobileNo

        userName = findViewById(R.id.userName)

        userName.text = sessionManager.fetchAuthACDetails().name

        drawerLayout = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.heshe, R.id.rfp, R.id.newsactivity
            ), drawerLayout
        )


        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.heshe -> {
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
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.heshe -> {
                startActivity(Intent(this, AboutHeSheActivity::class.java))
            }
            R.id.rfp -> {
                var url = "https://www.bhoomikavihar.in/HomeAndroid/DocumentsRFP"
                startActivity(
                    Intent(this, PdfViewActivity::class.java)
                        .putExtra("URL", url)
                )
            }
            R.id.newsactivity -> {
                var url = "https://www.bhoomikavihar.in/HomeAndroid/MediaCorner"
                startActivity(
                    Intent(this, PdfViewActivity::class.java)
                        .putExtra("URL", url)
                )
            }
        }
        drawerLayout.closeDrawers()
        return true
    }
}