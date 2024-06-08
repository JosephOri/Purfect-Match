package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookworms.R
import com.example.bookworms.models.entities.User
import com.example.bookworms.utils.Utils
import com.example.bookworms.viewModels.UserViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth
        if (auth.currentUser != null){
            Log.d("SignupActivity", "User is already logged in")
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
        addLinkToLogin()

        val nameField:TextInputEditText = findViewById(R.id.nameField)
        val phoneField:TextInputEditText = findViewById(R.id.phoneField)
        val emailField:TextInputEditText = findViewById(R.id.emailField)
        val passwordField:TextInputEditText = findViewById(R.id.passwordField)
        val confirmPasswordField:TextInputEditText = findViewById(R.id.confirmPasswordField)
        val signupButton = findViewById<MaterialButton>(R.id.signupButton)

        signupButton.setOnClickListener {
            val name = nameField.text.toString()
            val phone = phoneField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()
            val isInputValid = validateInput(name, phone, email, password, confirmPassword)
            if(!isInputValid){
                return@setOnClickListener
            }
            val user = User("", phone, name, email)
            userViewModel = UserViewModel()
            userViewModel.register(user, password, {
                Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }, {
                Toast.makeText(applicationContext, "User registration failed", Toast.LENGTH_SHORT).show()
            })

        }
    }

    private fun validateInput(name: String, phone: String, email: String, password: String, confirmPassword: String): Boolean {
       if(name.isEmpty()||phone.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
           Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT).show()
           return false
       }
        if(password != confirmPassword){
            Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Utils.isEmailValid(email)){
            Toast.makeText(applicationContext, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Utils.isNumeric(phone)){
            Toast.makeText(applicationContext, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun addLinkToLogin() {
        val linkToLogin: TextView = findViewById(R.id.linkToSignup)
        linkToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}





