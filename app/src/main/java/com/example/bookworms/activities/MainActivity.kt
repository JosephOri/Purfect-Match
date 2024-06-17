package com.example.bookworms.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bookworms.R
import com.example.bookworms.databinding.ActivityMainBinding
import com.example.bookworms.fragments.AddPostFragment
import com.example.bookworms.fragments.MapsFragment
import com.example.bookworms.fragments.MyPostsFragment
import com.example.bookworms.fragments.ProfilePageFragment
import com.example.bookworms.fragments.ProgressFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewBinding: ActivityMainBinding

    private val progressFragment = ProgressFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        auth = FirebaseAuth.getInstance()

        setEventListeners()
    }

    private fun setEventListeners() {
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewBinding.bottomNavigationView.setOnItemSelectedListener{
            showProgressFragment()

            Handler(Looper.getMainLooper()).postDelayed({
                when(it.itemId) {
                    R.id.profilePage -> replaceFragment(ProfilePageFragment())
                    R.id.addPostPage -> replaceFragment(AddPostFragment())
                    R.id.myPostsPage -> replaceFragment(MyPostsFragment())
                    R.id.mapPage -> replaceFragment(MapsFragment())
                    else -> {}
                }
                hideProgressFragment()
            }, 500)

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun showProgressFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.progressCircularFragment, progressFragment)
        fragmentTransaction.commit()
    }

    private fun hideProgressFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(progressFragment)
        fragmentTransaction.commit()
    }

}
