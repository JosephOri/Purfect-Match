package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bookworms.R
import com.example.bookworms.databinding.ActivityMainBinding
import com.example.bookworms.fragments.AboutFragment
import com.example.bookworms.fragments.AddPostFragment
import com.example.bookworms.fragments.HomePageFragment
import com.example.bookworms.fragments.MapsFragment
import com.example.bookworms.fragments.MyPostsFragment
import com.example.bookworms.fragments.ProfilePageFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewBinding: ActivityMainBinding

    private var loginButton: MaterialButton? = null
    private var signupButton: MaterialButton? = null
    private var logoutButton: MaterialButton? = null
    private var profileButton: MaterialButton? = null
    private var homePageButton: MaterialButton? = null
    private var aboutPageButton: MaterialButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        initButtons()

        val currentUser = auth.currentUser
        if (currentUser != null)
            showLoggedInButtons()
        else
            showWelcomeButtons()

        setEventListeners()
    }

    private fun initButtons(){
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)
        logoutButton = findViewById(R.id.logoutButton)
        profileButton = findViewById(R.id.profileButton)
        homePageButton = findViewById(R.id.homePageButton)
        aboutPageButton = findViewById(R.id.aboutPageButton)
    }

    private fun setEventListeners() {
        loginButton?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signupButton?.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        logoutButton?.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileButton?.setOnClickListener {
            replaceFragment(ProfilePageFragment(), "profilePage")
        }

        homePageButton?.setOnClickListener {
            replaceFragment(HomePageFragment(), "homePage")
        }

        aboutPageButton?.setOnClickListener {
            replaceFragment(AboutFragment(), "aboutPage")
        }

        viewBinding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.profilePage -> replaceFragment(ProfilePageFragment(), "profilePage")
                R.id.addPostPage -> replaceFragment(AddPostFragment(), "addPostPage")
                R.id.myPostsPage -> replaceFragment(MyPostsFragment(), "myPostsPage")
                R.id.mapPage -> replaceFragment(MapsFragment(), "mapPage")
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrameLayout, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    private fun showLoggedInButtons() {
        logoutButton?.visibility = View.VISIBLE

        profileButton?.visibility = View.VISIBLE
        homePageButton?.visibility = View.VISIBLE
        aboutPageButton?.visibility = View.VISIBLE

        loginButton?.visibility = View.INVISIBLE
        signupButton?.visibility = View.INVISIBLE
    }

    private fun showWelcomeButtons() {
        loginButton?.visibility = View.VISIBLE
        signupButton?.visibility = View.VISIBLE

        logoutButton?.visibility = View.INVISIBLE

        profileButton?.visibility = View.INVISIBLE
        homePageButton?.visibility = View.INVISIBLE
        aboutPageButton?.visibility = View.INVISIBLE
    }
}
