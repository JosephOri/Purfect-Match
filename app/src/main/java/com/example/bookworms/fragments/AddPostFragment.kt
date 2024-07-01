package com.example.bookworms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bookworms.R
import com.example.bookworms.models.entities.PostEntity
import com.google.android.material.textfield.TextInputEditText

/**
 * A simple `Fragment` subclass.
 * Use the `AddPostFragment.newInstance` factory method to create an instance of this fragment.
 */
class AddPostFragment : Fragment() {

    private lateinit var dummyPost: PostEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initDummyPost()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_post_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateUiWithDummyPost(view)

    }

    private fun populateUiWithDummyPost(view: View) {
        val titleInput = view.findViewById<TextInputEditText>(R.id.post_title_input)
        val descriptionInput = view.findViewById<TextInputEditText>(R.id.post_description_input)
        val addPostButton = view.findViewById<View>(R.id.addPostButton)
        addPostButton.setOnClickListener {
            // Add post to database
            Toast.makeText(context, "Post added successfully", Toast.LENGTH_SHORT).show()
        }

    }
}