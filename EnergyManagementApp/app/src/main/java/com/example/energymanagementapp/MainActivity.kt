package com.example.energymanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun submit(view: View){
        val username = findViewById<TextInputEditText>(R.id.usernameField);
        val password = findViewById<EditText>(R.id.passwordField);
        val usernameError = findViewById<TextView>(R.id.errorMsgUser)
        val passwordError = findViewById<TextView>(R.id.errorMsgPass)
        val errorMessage = findViewById<TextView>(R.id.errorMsg);
        var msgUser=""
        var msgPass=""

        if(username.text.toString().isEmpty()){
            msgUser = "Username field cannot be empty"
        }else{
            if(username.text.toString().length < 3){
                msgUser = "Username too short"
            }
        }

        if(password.text.isEmpty()){
            msgPass = "Password field cannot be empty"
        }else{
            if(password.text.length < 3){
                msgPass = "Password too short"
            }
        }

        if(msgUser.isEmpty() && msgPass.isEmpty()){
            if(username.text.toString() == "admin" && password.text.toString() == "password"){
                val intent = Intent ( this, MainPageActivity::class.java )
                startActivity ( intent )
            }else{
                errorMessage.text = "Wrong username or password!"
                usernameError.text = ""
                passwordError.text = ""
            }
        }else{
            usernameError.text = msgUser
            passwordError.text = msgPass
        }
    }
}