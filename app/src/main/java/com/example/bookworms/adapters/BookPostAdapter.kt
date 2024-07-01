package com.example.bookworms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookworms.models.entities.BookEntity
import com.example.bookworms.R
import com.google.android.material.imageview.ShapeableImageView

class BookPostAdapter (private val bookList: List<BookEntity>):
                RecyclerView.Adapter<BookPostAdapter.BookViewHolder>(){

    class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bookTitleTextView: TextView = itemView.findViewById(R.id.recyclerItemBookTitle)
        val bookImageView: ShapeableImageView = itemView.findViewById(R.id.recyclerItemImage)
        val bookAuthorTextView: TextView = itemView.findViewById(R.id.recyclerItemBookAuthor)
        val bookGenresTextView: TextView = itemView.findViewById(R.id.recyclerItemBookGenres)
        val bookRatingTextView: TextView = itemView.findViewById(R.id.recyclerItemBookRating)
        val bookDescriptionTextView: TextView = itemView.findViewById(R.id.recyclerItemBookDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item, parent, false) // Use your list item layout
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.bookTitleTextView.text = currentBook.title
        holder.bookAuthorTextView.text = currentBook.author
        holder.bookGenresTextView.text = currentBook.genres
        holder.bookRatingTextView.text = currentBook.rating
        holder.bookDescriptionTextView.text = currentBook.description
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

}