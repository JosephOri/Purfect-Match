package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookworms.R
import com.example.bookworms.databinding.ActivityMainBinding
import com.example.bookworms.databinding.FragmentProfilePageBinding
import com.example.bookworms.viewModels.UserViewModel

import com.example.bookworms.fragments.ProgressFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewBinding: ActivityMainBinding

    private val progressFragment = ProgressFragment()

    private lateinit var userViewModel: UserViewModel

    private var bottomNavBar : BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        initParameters()

        val currentUser = auth.currentUser
        if(currentUser != null) {
            Log.d("MainActivity", "Current user details: email: ${currentUser.email}, UID: ${currentUser.uid}")
        } else {
            Log.d("MainActivity", "No user logged in")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setEventListeners()
    }


    private fun initParameters(){
        bottomNavBar = findViewById(R.id.bottomNavigationView)
        userViewModel = UserViewModel()
    }

    private fun setEventListeners() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFrameLayout)
        val navController = navHostFragment?.findNavController()
        if(navController != null) {
            navController.addOnDestinationChangedListener { _, _, _ ->
                lifecycleScope.launch {
                    showProgressFragment()
                    delay(500)
                    hideProgressFragment()
                }
            }

            bottomNavBar?.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homePage -> navController.navigate(R.id.homePageFragment)
                    R.id.profilePage -> navController.navigate(R.id.profilePageFragment)
                    R.id.addPostPage -> navController.navigate(R.id.addPostFragment)
                    R.id.myPostsPage -> navController.navigate(R.id.myPostsFragment)
                    R.id.mapPage -> navController.navigate(R.id.mapsFragment)
                }
                true
            }

        }
    }

    private fun showProgressFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.progressCircularFragment, progressFragment)
        fragmentTransaction.commit()
    }

    private fun hideProgressFragment() {
        supportFragmentManager.beginTransaction().remove(progressFragment).commit()
    }
}
