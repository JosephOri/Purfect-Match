package com.purfectmatch.ViewModel

import androidx.lifecycle.ViewModel
import com.purfectmatch.Model.Entities.PostEntity
import com.purfectmatch.Model.JoinedModel.JoinedPostModel

class EditPostViewModel: ViewModel() {
    val postsModel = JoinedPostModel()

    fun editPost(post : PostEntity, callback: (Boolean) -> Unit){
        postsModel.editPost(post, callback)
    }
}