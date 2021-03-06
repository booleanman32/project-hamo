package com.example.project_hamo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        registerButton_registerActivity.setOnClickListener {
            performRegister();
        }

        loginTextview_registerActivity.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
        }







    }

    private fun performRegister(){
        val name = nameEditText_registerActivity.text.toString();
        val email = emailEditText_registerActivity.text.toString();
        val password = passwordEditText_registerActivity.text.toString();
        val rePassword = rePasswordEditText_registerActivity.text.toString();
        Log.d("Register",  name);
        Log.d("Register",  email);
        Log.d("Register",  password);
        Log.d("Register",  rePassword);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(!it.isSuccessful) return@addOnCompleteListener
            Log.d("Main", "Successfully created user with uid: ${it?.result?.user?.uid}")
            val text = "Successfully login user with uid: ${it?.result?.user?.uid}"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            saveUserToDatabase()
        }
            .addOnFailureListener{
                Log.d("Main", "failed created user : ${it.message}")
            }
    }

    private fun saveUserToDatabase(){
        val uid = FirebaseAuth.getInstance().uid
        val ref =   FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = uid?.let { User(it, nameEditText_registerActivity.text.toString(), emailEditText_registerActivity.text.toString()) }
        ref.setValue(user)
    }
}

class User(val uid: String, val name: String, val email: String);
