package com.purfectmatch.Model.ModelRoom.Model

import com.purfectmatch.Model.Entities.PostEntity
import com.purfectmatch.Model.ModelRoom.AppLocalDB


class PostModel {
    fun getAllPosts(): List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getAllPosts()
    }

    fun insertPost(post: PostEntity) {
        val db = AppLocalDB.getInstance().postDao().insertPost(post)
    }
     fun deletePost(post: PostEntity){
         return AppLocalDB.getInstance().postDao().deletePost(post)
     }
    fun getPostsByUid(uid: String) : List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getPostsByUserId(uid)
    }
    fun updatePost(post: PostEntity){
        return AppLocalDB.getInstance().postDao().updatePost(post)
    }

}