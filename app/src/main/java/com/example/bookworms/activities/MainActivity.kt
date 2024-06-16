package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bookworms.R
import com.example.bookworms.databinding.ActivityMainBinding
import com.example.bookworms.fragments.AddPostFragment
import com.example.bookworms.fragments.HomePageFragment
import com.example.bookworms.fragments.MapsFragment
import com.example.bookworms.fragments.MyPostsFragment
import com.example.bookworms.fragments.ProfilePageFragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewBinding: ActivityMainBinding

    private val loginButton get() = viewBinding.loginButton
    private val signupButton get() = viewBinding.signupButton
    private val logoutButton get() = viewBinding.logoutButton
    private val progressIndicator get() = viewBinding.spinnerProgressIndicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        setEventListeners()
        updateUI(auth.currentUser != null)
    }

    override fun onStart() {
        super.onStart()
        progressIndicator.visibility = View.VISIBLE
        updateUI(auth.currentUser != null)

    }

    private fun setEventListeners() {
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        if(auth.currentUser == null) {
            loginButton.visibility = View.VISIBLE
            signupButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        } else {
            loginButton.visibility = View.GONE
            signupButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
        }

        viewBinding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.homePage -> replaceFragment(HomePageFragment())
                R.id.profilePage -> replaceFragment(ProfilePageFragment())
                R.id.addPostPage -> replaceFragment(AddPostFragment())
                R.id.myPostsPage -> replaceFragment(MyPostsFragment())
                R.id.mapPage -> replaceFragment(MapsFragment())

                else -> {
                    Log.e("MainActivity", "Unknown navigation item selected: ${it.itemId}")
                    Toast.makeText(this, "Unknown navigation item selected", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        progressIndicator.visibility = View.VISIBLE
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        supportFragmentManager.addOnBackStackChangedListener {
                progressIndicator.visibility = View.INVISIBLE
        }
//        progressIndicator.visibility = View.GONE
    }

    private fun updateUI(isLoggedIn: Boolean) {
        if(isLoggedIn) {
            loginButton.visibility = View.GONE
            signupButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE

            logoutButton.setOnClickListener {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            loginButton.visibility = View.VISIBLE
            signupButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        }
        progressIndicator.visibility = View.GONE
    }
}