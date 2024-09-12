package eu.tutorials.practiceapp

data class Todo(
    var text: String,
    var tag: String,
    var time: String,
    var alarm: String,
    var period: String,
    var repeat: Int,
    var done: Boolean = false,
    var onEdit: Boolean = true
)
