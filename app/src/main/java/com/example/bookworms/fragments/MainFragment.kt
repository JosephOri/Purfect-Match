package com.example.bookworms.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookworms.R
import com.example.bookworms.activities.LoginActivity
import com.example.bookworms.databinding.FragmentMainBinding
import com.example.bookworms.viewModels.UserViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var fragmentViewBinding: FragmentMainBinding

    private var logoutButton: MaterialButton? = null
    private var profileButton: MaterialButton? = null
    private var homePageButton: MaterialButton? = null
    private var aboutPageButton: MaterialButton? = null

    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initParameters(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = UserViewModel()
        setEventListeners()
    }

    private fun initParameters(view: View){
        logoutButton = view.findViewById(R.id.logoutButton)
        profileButton = view.findViewById(R.id.profileButton)
        homePageButton = view.findViewById(R.id.homePageButton)
        aboutPageButton = view.findViewById(R.id.aboutPageButton)

        fragmentViewBinding = FragmentMainBinding.inflate(layoutInflater)

    }

    private fun setEventListeners(){

        val navController = findNavController()

        logoutButton?.setOnClickListener {
            userViewModel.logout {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        profileButton?.setOnClickListener {
            navController.navigate(R.id.profilePageFragment)
        }

        homePageButton?.setOnClickListener {
            navController.navigate(R.id.homePageFragment)
        }

        aboutPageButton?.setOnClickListener {
            navController.navigate(R.id.aboutFragment)
        }

    }


}