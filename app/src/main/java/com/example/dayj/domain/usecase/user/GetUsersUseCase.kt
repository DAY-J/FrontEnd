package com.example.dayj.domain.usecase.user

import com.example.dayj.data.repo.UserRepository
import com.example.dayj.util.toResultFlow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository::getUsers.toResultFlow()
}