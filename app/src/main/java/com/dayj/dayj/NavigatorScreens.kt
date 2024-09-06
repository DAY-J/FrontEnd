package com.dayj.dayj

enum class NavigatorScreens {
    Main,
    PostingDetail,
    WritePosting,
    SearchPosting,
    AddToDo
}

interface ScreenNavigation {
    val route: String
    val sendRoute: String
}

sealed class ScreenType(
    override val route: String, override val sendRoute: String = ""
) : ScreenNavigation {
    data class UpdateTodo(val passItem: String = "passItem") :
        ScreenType("updateTodo/{${passItem}}", "updateTodo/${passItem}")
}
