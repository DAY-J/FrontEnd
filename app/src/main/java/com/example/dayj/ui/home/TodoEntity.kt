package com.example.dayj.ui.home

data class TodoEntity(
    var title: String,
    var tag: String,
    var recommendedTitle: String,
    var time: String,
    var alarm: Boolean = true,
    var period: String,
    var repeat: String,
    var done: Boolean = false,
    var onEdit: Boolean = false
)