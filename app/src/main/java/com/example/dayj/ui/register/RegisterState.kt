package com.example.dayj.ui.register

data class RegisterState(
    val email: String = "",
    val nickName: String = "",
    val nickNameErrorType: NickNameErrorType = NickNameErrorType.Idle
)


sealed interface NickNameErrorType {
    data object Idle : NickNameErrorType
    data object NotInvalid : NickNameErrorType
    data object Duplicated : NickNameErrorType
}


fun NickNameErrorType.toMessage(): String =
    when (this) {
        NickNameErrorType.Duplicated -> "이미 사용중인 닉네임입니다."
        NickNameErrorType.Idle -> ""
        NickNameErrorType.NotInvalid -> "한글 15자, 영문 30자까지 가능합니다."
    }
