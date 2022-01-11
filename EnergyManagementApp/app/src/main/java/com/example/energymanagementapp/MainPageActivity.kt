package com.example.energymanagementapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainPageActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle =  ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_battery -> Toast.makeText(applicationContext, "Clicked battery", Toast.LENGTH_SHORT).show()
                R.id.nav_devices -> Toast.makeText(applicationContext, "Clicked devices", Toast.LENGTH_SHORT).show()
                R.id.nav_consumption -> Toast.makeText(applicationContext, "Clicked consumption", Toast.LENGTH_SHORT).show()
                R.id.nav_slope -> Toast.makeText(applicationContext, "Clicked slope", Toast.LENGTH_SHORT).show()
                R.id.nav_efficiency -> Toast.makeText(applicationContext, "Clicked efficiency", Toast.LENGTH_SHORT).show()
                R.id.nav_provider -> Toast.makeText(applicationContext, "Clicked provider", Toast.LENGTH_SHORT).show()
                R.id.nav_ecar -> Toast.makeText(applicationContext, "Clicked ecar", Toast.LENGTH_SHORT).show()
                R.id.logout -> logout()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        logout()
    }

    private fun logout(){
        val builder = AlertDialog.Builder ( this )
        builder.setTitle ( "Confirmation" )
            .setMessage ( "Do you really want to log out?" )
            .setPositiveButton ( "Confirm", DialogInterface.OnClickListener(confirmListener) )
            .setNegativeButton ( "Cancel", DialogInterface.OnClickListener(cancelListener) )
        builder.create().show()
    }

    private val confirmListener = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,"Logged out",Toast.LENGTH_SHORT).show()
        val i = Intent(this, MainActivity::class.java);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        this.finish();
    }

    private val cancelListener = {dialog: DialogInterface, which: Int ->

    }
}