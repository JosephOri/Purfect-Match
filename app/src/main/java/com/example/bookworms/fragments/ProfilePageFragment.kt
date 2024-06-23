package com.example.bookworms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.bookworms.R
import com.example.bookworms.viewModels.ProfileViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_profile_page, container, false)
        initializeParameters()
        initializeFirebase()
        return view
    }

    private fun initializeParameters(){
        Log.d("ProfilePageFragment", "initializeParameters")
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        profileImageView = view?.findViewById(R.id.profileCircleImageView)
        profileUserNameTextView = view?.findViewById(R.id.profileUserNameTextView)
        profileEmailTextView = view?.findViewById(R.id.profileEmailTextView)
        profilePhoneTextView = view?.findViewById(R.id.profilePhoneTextView)
        profileBioTextView = view?.findViewById(R.id.profileBioTextView)
        profileDateJoinedTextView = view?.findViewById(R.id.profileDateJoinedTextView)

        myPostsButton = view?.findViewById(R.id.profileMyPostsButton)
        editProfileButton = view?.findViewById(R.id.profilePageEditProfileBtn)
        logoutButton = view?.findViewById(R.id.profilePageLogoutBtn)

        Log.d("ProfilePageFragment", "initializeParameters: Done")
    }

    private fun initializeFirebase() {
        Log.d("ProfilePageFragment", "initializeFirebase")
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference

        val currentUser = firebaseAuth.currentUser
        val uid = currentUser!!.uid

        // Use the profileViewModel to write a getUserById(uid) function that fetches the user data from the Firestore database.
        profileViewModel.getUserByUid(uid) { userEntity ->
            if (userEntity != null) {
                Log.d("ProfilePageFragment", "initializeFirebase: userEntity: $userEntity")
                profileUserNameTextView?.text = userEntity.name
                profileEmailTextView?.text = userEntity.email
                profilePhoneTextView?.text = userEntity.phone
                profileBioTextView?.text = "I Love Books"
                profileDateJoinedTextView?.text = currentUser.metadata?.creationTimestamp.toString()
            }
            Log.d("ProfilePageFragment", "firebaseAuth.currentUser.UID: $uid")
            Log.d("ProfilePageFragment","initializeFirebase: Done. firebaseAuth.currentUser.UID: $uid"
            )
        }
    }

}