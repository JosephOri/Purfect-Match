package com.example.bookworms.fragments

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
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
import com.example.bookworms.databinding.FragmentProfilePageBinding
import com.example.bookworms.models.entities.User
import com.example.bookworms.viewModels.ProfileViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale


class ProfilePageFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference
    private var selectedImageUri: Uri? = null
    private val USERS_PROFILE_IMAGES_PATH = "profile_images/"
    private var fileRefName: String = ""

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileViewBinding: FragmentProfilePageBinding

    private var profileImageView: CircleImageView? = null
    private var profileUserNameTextView: TextView? = null
    private var profileEmailTextView: TextView? = null
    private var profilePhoneTextView: TextView? = null
    private var profileBioTextView: TextView? = null
    private var profileDateJoinedTextView: TextView? = null

    private var myPostsButton: MaterialButton? = null
    private var editProfileButton: MaterialButton? = null
    private var logoutButton: MaterialButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
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
            if (navController.currentDestination?.id == R.id.profilePageFragment) {
                navController.navigate(R.id.action_profilePageFragment_to_myPostsFragment)
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }
        }

        editProfileButton?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.profilePageFragment) {
                navController.navigate(R.id.action_profilePageFragment_to_editProfilePageFragment)
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }
        }

        logoutButton?.setOnClickListener {
            profileViewModel.logout {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun initializeParameters(view: View){
        profileViewBinding = FragmentProfilePageBinding.inflate(layoutInflater)
        profileViewModel = ProfileViewModel()

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
        storageRef = FirebaseStorage.getInstance().getReference(USERS_PROFILE_IMAGES_PATH)

        currentUser = firebaseAuth.currentUser!!

        storageRef.listAll().addOnSuccessListener { result ->
            fileRefName = result.items[0].name
        }.addOnFailureListener { e ->
            Log.e("ProfilePageFragment", "Failed to list items in storageRef: ${e.message}")
        }
        println("ProfilePageFragment: fileRefName = result.items[0].name: $fileRefName")

        val uid = currentUser.uid
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            profileViewModel.getUserByUid(uid) { userEntity ->
                if(userEntity != null){
                    setProfileData(userEntity)
                } else {
                    Log.d("ProfilePageFragment", "Failed to retrieve user data for UID: $uid")
                }
            }
        }
    }

    private fun setProfileData(userEntity: User){
        profileUserNameTextView?.text = userEntity.name
        profileEmailTextView?.text = userEntity.email
        profilePhoneTextView?.text = userEntity.phone
        profileBioTextView?.text = "I Love Books"

        val creationTimestamp = firebaseAuth.currentUser?.metadata?.creationTimestamp
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val creationDate = sdf.format(Date(creationTimestamp!!))
        profileDateJoinedTextView?.text = creationDate
    }
}