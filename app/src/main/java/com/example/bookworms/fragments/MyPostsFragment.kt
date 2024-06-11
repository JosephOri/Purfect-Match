package com.example.bookworms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookworms.R

/**
 * A simple `Fragment` subclass.
 * Use the `MyPostsFragment.newInstance` factory method to
 * create an instance of this fragment.
 */
class MyPostsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_my_posts, container, false)
    }
}