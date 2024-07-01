package com.example.bookworms.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.bookworms.R
import com.example.bookworms.databinding.FragmentEditProfilePageBinding
import com.example.bookworms.databinding.FragmentProfilePageBinding
import com.example.bookworms.viewModels.ProfileViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

class EditProfilePageFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private var selectedImageUri: Uri? = null
    private val USERS_PROFILE_IMAGES_PATH = "profile_images/"

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var editProfileViewBinding: FragmentEditProfilePageBinding

    private var editProfileImageView: CircleImageView? = null
    private var profileUserNameEditText: EditText? = null
    private var profileEmailEditText: EditText? = null
    private var profilePhoneEditText: EditText? = null
    private var profileBiolEditText: EditText? = null

    private var editProfileImageButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var cancelButton: MaterialButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile_page, container, false)
        initializeParameters(view)
        initializeFirebase()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun initializeParameters(view: View) {
        editProfileViewBinding = FragmentEditProfilePageBinding.bind(view)
        profileViewModel = ProfileViewModel()

        editProfileImageView = view.findViewById(R.id.editProfileCircleImageView)
        profileUserNameEditText = view.findViewById(R.id.editProfileUserNameEditText)
        profileEmailEditText = view.findViewById(R.id.editProfileEmailEditText)
        profilePhoneEditText = view.findViewById(R.id.editProfilePhoneEditText)
        profileBiolEditText = view.findViewById(R.id.editProfileBioEditText)

        editProfileImageButton = view.findViewById(R.id.editProfileChangeImageButton)
        saveButton = view.findViewById(R.id.editProfilePageSaveButton)
        cancelButton = view.findViewById(R.id.editProfilePageCancelButton)
    }

    private fun initializeFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser!!
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference
    }

    private fun setupClickListeners() {
        val navController = findNavController()

        editProfileImageButton?.setOnClickListener {
            // Open gallery to select image
        }

        saveButton?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.editProfilePageFragment) {
                // Save profile changes
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }
        }

        cancelButton?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.editProfilePageFragment) {
                navController.navigate(R.id.action_editProfilePageFragment_to_profilePageFragment)
            } else {
                Log.e("ProfilePageFragment", "Invalid navigation state")
            }
        }
    }
}
