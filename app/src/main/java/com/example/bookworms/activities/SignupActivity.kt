package com.example.bookworms.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bookworms.R
import com.example.bookworms.Model.entities.User
import com.example.bookworms.utils.Utils
import com.example.bookworms.viewModels.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    private lateinit var cpi : CircularProgressIndicator

    private lateinit var fullNameInputTextView:TextInputEditText
    private lateinit var phoneInputTextView:TextInputEditText
    private lateinit var emailInputTextView:TextInputEditText
    private lateinit var passwordInputTextView:TextInputEditText
    private lateinit var confirmPasswordInputTextView:TextInputEditText
    private lateinit var signupButton: MaterialButton

    private lateinit var uploadImageButton: MaterialButton
    private lateinit var profileImageView : ImageView
    private lateinit var imageUrlRef : String

    private var imageUri: Uri? = null
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                Picasso.get().load(imageUri).into(profileImageView)
                //uploadImageFromPicasso()
                //uploadImage()
            }
        }

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
        initParameters()
        setOnClickListeners()
    }

    private fun signUpUser(
        fullNameInputTextView: TextInputEditText,
        phoneInputTextView: TextInputEditText,
        emailInputTextView: TextInputEditText,
        passwordInputTextView: TextInputEditText,
        confirmPasswordInputTextView: TextInputEditText
    ) {
        val name = fullNameInputTextView.text.toString()
        val phone = phoneInputTextView.text.toString()
        val email = emailInputTextView.text.toString()
        val password = passwordInputTextView.text.toString()
        val confirmPassword = confirmPasswordInputTextView.text.toString()
        val isInputValid = validateInput(name, phone, email, password, confirmPassword)
        if(!isInputValid){
            return
        }
        val user = User("", phone, name, email)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.register(user, password){isSuccessful ->
            if(isSuccessful){
                Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            if(!isSuccessful){
                Toast.makeText(applicationContext, "User registration failed", Toast.LENGTH_SHORT).show()
                return@register
            }

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
        val linkToLogin: TextView = findViewById(R.id.linkToLogin)
        linkToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initParameters(){
        cpi = findViewById(R.id.signupCircularProgressIndicator)

        fullNameInputTextView = findViewById(R.id.activity_signup_full_name_input)
        phoneInputTextView = findViewById(R.id.activity_signup_phone_input)
        emailInputTextView = findViewById(R.id.activity_signup_email_input)
        passwordInputTextView = findViewById(R.id.activity_signup_password_input)
        confirmPasswordInputTextView = findViewById(R.id.activity_signup_confirm_password_input)

        signupButton = findViewById(R.id.signupButton)
        uploadImageButton = findViewById(R.id.activity_signup_profile_image_button)
        profileImageView = findViewById(R.id.activity_signup_profile_image)

    }

    private fun setOnClickListeners(){
        uploadImageButton.setOnClickListener {
            imagePicker.launch("image/*")
        }

        signupButton.setOnClickListener {
            signUpUser(fullNameInputTextView, phoneInputTextView, emailInputTextView, passwordInputTextView, confirmPasswordInputTextView)
        }
    }

    /*private fun uploadImage(){
        imageUri?.let{ uri ->
            Log.d("SignupActivity", "Image URI: $uri")

            Log.d("SignupActivity", "File exists")

            val storageReference = FirebaseStorage.getInstance().getReference("profile_images/${System.currentTimeMillis()}.jpg")
            storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                Log.d("SignupActivity", "Image uploaded successfully")
                Log.d("SignupActivity", "Image URL: ${taskSnapshot.metadata?.path}")

                // Get the download URL of the uploaded image
                storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()
                    imageUrlRef = imageUrl
                    // Load the image into your ImageView using Glide
                    Glide.with(this).load(imageUrl).into(profileImageView)

                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener { e ->
                    Toast.makeText(this, "Error occurred. Is Completed?: ${e.isComplete}", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener{ e ->
                Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun uploadImageFromPicasso(){
        Picasso.get().load(imageUri).into(profileImageView)
    }*/
}