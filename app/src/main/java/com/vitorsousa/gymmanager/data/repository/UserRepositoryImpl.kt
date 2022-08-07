package com.vitorsousa.gymmanager.data.repository

import com.google.firebase.firestore.CollectionReference
import com.vitorsousa.gymmanager.core.Constants.USERS
import com.vitorsousa.gymmanager.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @Named(USERS) private val userRef: CollectionReference,
): UserRepository {

    override suspend fun saveUser(id: String, nome: String, email: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            val user = hashMapOf(
                "nome" to nome,
                "email" to email
            )
            try {
                userRef.document(id).set(user).await()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }


}