package com.dayj.dayj.home.addtodo



sealed interface TodoViewEffect {

    data class ShowToast(val message : String) : TodoViewEffect

    data object CompleteSave : TodoViewEffect

}