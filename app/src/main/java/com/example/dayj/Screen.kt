package com.example.dayj

sealed class Screen(val route:String) {
    object HomeScreen:Screen("homescreen")
    object AddTodoScreen:Screen("addtodoscreen")
}