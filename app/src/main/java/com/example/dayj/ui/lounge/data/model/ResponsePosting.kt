package com.example.dayj.ui.lounge.data.model

data class ResponsePosting(
    val id: Int,
    val appUserId: Int,
    val author: String,
    val postView: Int,
    val postLike: Int,
    val postTitle: String,
    val postContent: String,
    val postTag: String,
    val isAnonymous: Boolean,
    val postPhoto: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val comment: List<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel>
) {
    data class CommentModel(
        val id: Int,
        val parentId: Int,
        val content: String,
        val isAnonymous: Boolean,
        val author: String,
        val createdAt: String
    )
}
