package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookworms.R
import com.example.bookworms.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        if (auth.currentUser != null){
            Log.d("LoginActivity", "User is already logged in")
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
        addLinkToSignUp()

        val emailField: TextInputEditText = findViewById(R.id.activity_login_email_input)
        val passwordField: TextInputEditText = findViewById(R.id.activity_login_password_input)
        val loginButton = findViewById<Button>(R.id.signupButton)

        loginButton.setOnClickListener {
            logInUser(emailField, passwordField)
        }

    }
    private fun logInUser(
        emailField: TextInputEditText,
        passwordField: TextInputEditText
    ) {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        val isInputValid = validateSignIn(email, password)
        if (!isInputValid) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LoginActivity", "signInWithEmail:success")
                    val user = auth.currentUser
                    val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mainActivityIntent)
                } else {
                    Toast.makeText(baseContext, "invalid credentials, please try again.",
                        Toast.LENGTH_SHORT).show()
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                }
            }
    }

    private fun validateSignIn(email: String, password: String): Boolean {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Utils.isEmailValid(email)){
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun addLinkToSignUp() {
        val linkToSignUp: TextView = findViewById(R.id.linkToSignup)
        linkToSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}

