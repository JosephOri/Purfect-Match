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
import com.example.bookworms.R
import com.example.bookworms.models.entities.User
import com.example.bookworms.utils.Utils
import com.example.bookworms.viewModels.UserViewModel
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
                // set the same image in an ImageView with id of "activityMainProfileImageView" in the main_activity.xml layout
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                Log.d("SignupActivity", "Image URL: ${it.path}")
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

        userViewModel = UserViewModel()
        val user = User("", name, email, phone, imageUri.toString())
        userViewModel.register(user, password){isSuccessful ->
            if(isSuccessful){
                Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            if(!isSuccessful){
                Toast.makeText(applicationContext, "User registration failed", Toast.LENGTH_SHORT).show()
                //uploadImage()
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


        fullNameInputTextView = findViewById(R.id.activity_signup_user_name_input)
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

    private fun uploadImage(){
        imageUri?.let{ uri ->
            Log.d("SignupActivity", "uploadImage(): Image URI: $uri")

            val storageReference = FirebaseStorage.getInstance().getReference("profile_images/${System.currentTimeMillis()}.jpg")
            Log.d("SignupActivity", "uploadImage(): storageReference.name: ${storageReference.name}")
            Log.d("SignupActivity", "uploadImage(): storageReference.path: ${storageReference.path}")
            Log.d("SignupActivity", "uploadImage(): storageReference.downloadUrl: ${storageReference.downloadUrl}")
            Log.d("SignupActivity", "uploadImage(): storageReference.bucket: ${storageReference.bucket}")
            Log.d("SignupActivity", "uploadImage(): storageReference.root: ${storageReference.root}")
            Log.d("SignupActivity", "uploadImage(): storageReference.parent: ${storageReference.parent}")

            storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                Log.d("SignupActivity", "uploadImage(): Image uploaded successfully")
                Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    imageUri = downloadUri
                }.addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Image upload failed: ${e.message}", Toast.LENGTH_SHORT ).show()
                }
            }.addOnFailureListener { e ->
                Log.e("SignupActivity", "uploadImage(): Image upload failed: ${e.message}")
                Toast.makeText(applicationContext,"Image upload failed: ${e.message}",Toast.LENGTH_SHORT).show()
            }
        }
    }
}