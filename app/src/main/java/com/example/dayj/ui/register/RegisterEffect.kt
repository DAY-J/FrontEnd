package com.example.dayj.ui.register

sealed interface RegisterEffect {
    data class ShowToast(val message: String) : RegisterEffect
    data object RouteHome : RegisterEffect
}