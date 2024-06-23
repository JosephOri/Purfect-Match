package com.example.bookworms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookworms.Model.entities.BookEntity
import com.example.bookworms.R
import com.example.bookworms.adapters.BookPostAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        initializeParameters(view)
        return view
    }

    private fun initializeParameters(view : View) {
        bookList = arrayListOf(
            BookEntity("1", "The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/71aFt4+OTOL.jpg", "Fiction", "4.5", "An allegorical novel, The Alchemist follows a young Andalusian shepherd in his journey to the pyramids of Egypt, after having a recurring dream of finding a treasure there."),

        )

        recyclerView = view.findViewById(R.id.homePageBooksList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = BookPostAdapter(bookList)
    }

/*    private fun fetchBooksFromDb(onSuccess: () -> Unit) {

    }*/

}