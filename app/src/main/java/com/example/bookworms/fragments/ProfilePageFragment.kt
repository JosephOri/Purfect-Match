package com.example.bookworms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookworms.R


/**
 * A simple `Fragment` subclass.
 * Use the `ProfilePageFragment.newInstance` factory method to
 * create an instance of this fragment.
 */
class ProfilePageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

}