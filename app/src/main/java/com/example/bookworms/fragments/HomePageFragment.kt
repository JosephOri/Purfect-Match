package com.example.bookworms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookworms.Model.entities.BookEntity
import com.example.bookworms.R

/**
 * A simple `Fragment` subclass.
 * Use the `HomePageFragment.newInstance` factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {
    //    TODO: firebase DB, storage reference,
    //    initialize parameters of firestore instance


    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<BookEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_page, container, false)
        return view
    }

//    private fun initializeParameters(view : View) {
//        bookList = arrayListOf()
//
//        recyclerView = view.findViewById(R.id.homePageBooksListRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.setHasFixedSize(true)
//    }
//
//    private fun fetchBooksFromDb(onSuccess: () -> Unit) {
////        TODO: fetch books from firestore
//    }

}