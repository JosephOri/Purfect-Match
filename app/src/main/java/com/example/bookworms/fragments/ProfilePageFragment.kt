package com.example.bookworms.fragments

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookworms.R
import com.example.bookworms.activities.LoginActivity
import com.example.bookworms.activities.MainActivity
import com.example.bookworms.viewModels.ProfileViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale


class ProfilePageFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private lateinit var profileViewModel: ProfileViewModel

    private var profileImageView: CircleImageView? = null
    private var profileUserNameTextView: TextView? = null
    private var profileEmailTextView: TextView? = null
    private var profilePhoneTextView: TextView? = null
    private var profileBioTextView: TextView? = null
    private var profileDateJoinedTextView: TextView? = null

    private var myPostsButton: MaterialButton? = null
    private var editProfileButton: MaterialButton? = null
    private var logoutButton: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_profile_page, container, false)
        initializeParameters(view)
        initializeFirebase()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()

    }

    private fun setupClickListeners(){
        val navController = findNavController()

        myPostsButton?.setOnClickListener {
            Log.d("ProfilePageFragment", "Current destination: ${navController.currentDestination}")

            if (navController.currentDestination?.id == R.id.profilePageFragment) {
                navController.navigate(R.id.action_profilePageFragment_to_myPostsFragment)
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }
        }

        editProfileButton?.setOnClickListener {
            Log.d("ProfilePageFragment", "Current destination: ${navController.currentDestination}")

            if (navController.currentDestination?.id == R.id.profilePageFragment) {
                navController.navigate(R.id.action_profilePageFragment_to_editProfilePageFragment)
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }        }

        logoutButton?.setOnClickListener {
            profileViewModel.logout()
        }
    }


    private fun initializeParameters(view: View){
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        profileImageView = view.findViewById(R.id.profileCircleImageView)
        profileUserNameTextView = view.findViewById(R.id.profileUserNameTextView)
        profileEmailTextView = view.findViewById(R.id.profileEmailTextView)
        profilePhoneTextView = view.findViewById(R.id.profilePhoneTextView)
        profileBioTextView = view.findViewById(R.id.profileBioTextView)
        profileDateJoinedTextView = view.findViewById(R.id.profileDateJoinedTextView)

        myPostsButton = view.findViewById(R.id.profileMyPostsButton)
        editProfileButton = view.findViewById(R.id.profilePageEditProfileBtn)
        logoutButton = view.findViewById(R.id.profilePageLogoutBtn)
    }

    private fun initializeFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference

        val currentUser = firebaseAuth.currentUser
        if(currentUser == null){
            Log.d("ProfilePageFragment", "initializeFirebase: currentUser is null")
            return
        }

        val uid = currentUser.uid
        Log.d("ProfilePageFragment", "Fetching user data for UID: $uid")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            profileViewModel.getUserByUid(uid) { userEntity ->
                if(userEntity != null){
                    Log.d("ProfilePageFragment", "User data fetched successfully: $userEntity")
                    profileUserNameTextView?.text = userEntity.name
                    profileEmailTextView?.text = userEntity.email
                    profilePhoneTextView?.text = userEntity.phone
                    profileBioTextView?.text = "I Love Books"

                    val creationTimestamp = currentUser.metadata?.creationTimestamp
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val creationDate = sdf.format(Date(creationTimestamp!!))
                    profileDateJoinedTextView?.text = creationDate
                } else {
                    Log.d("ProfilePageFragment", "Failed to retrieve user data for UID: $uid")
                }
            }
        }

    }

}