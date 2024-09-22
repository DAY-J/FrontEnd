package com.example.dayj.ui.home.addtodo



sealed interface TodoViewEffect {

    data class ShowToast(val message : String) : TodoViewEffect

    data object CompleteSave : TodoViewEffect

}