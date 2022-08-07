package com.vitorsousa.gymmanager.domain.repositories

interface UserRepository {
    suspend fun saveUser(id: String, nome: String, email: String): Result<Unit>
}