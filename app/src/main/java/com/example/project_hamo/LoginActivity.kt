package com.example.project_hamo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton_loginActivity.setOnClickListener {
            val email = emailEditText_loginActivity.text.toString();
            val password = passwordEditText_loginActivity.text.toString();
            Log.d("Login",  email);
            Log.d("Login",  password);
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Main", "Successfully login user with uid: ${it?.result?.user?.uid}")
                    val text = "Successfully login user with uid: ${it?.result?.user?.uid}"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
                .addOnFailureListener{
                    Log.d("Main", "failed login user : ${it.message}")
                }
        }
        registerTextview_loginActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }
    }
}
