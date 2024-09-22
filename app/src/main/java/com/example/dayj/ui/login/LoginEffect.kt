package com.example.dayj.ui.login


sealed interface LoginEffect {

    data object RouteToMain : LoginEffect

    data class RouteToRegister(val email: String) : LoginEffect

    data class ShowToast(val message: String) : LoginEffect
}