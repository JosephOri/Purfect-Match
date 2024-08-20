package com.purfectmatch.ViewModel

import androidx.lifecycle.ViewModel
import com.purfectmatch.Model.Entities.PostEntity
import com.purfectmatch.Model.JoinedModel.JoinedPostModel

class UploadPostViewModel : ViewModel() {
    val postsModel = JoinedPostModel()
    fun uploadPost(post : PostEntity, callback: (Boolean) -> Unit){
        postsModel.uploadPost(post, callback)
    }
}