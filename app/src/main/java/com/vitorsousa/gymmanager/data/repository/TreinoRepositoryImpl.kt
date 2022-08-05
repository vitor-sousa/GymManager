package com.vitorsousa.gymmanager.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.vitorsousa.gymmanager.domain.models.Treino
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TreinoRepositoryImpl @Inject constructor(
    private val treinoRef: CollectionReference,
): TreinoRepository {

    override suspend fun addTreino(treino: Treino): Result<Treino> =
        withContext(Dispatchers.IO) {
            try {
                val id = treinoRef.document().id
                treinoRef.document(id).set(treino).await()
                return@withContext Result.success(treino)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
    }

    override suspend fun getAllTreinos(): Result<List<Treino>?> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val snapshot = treinoRef
                    .orderBy("data", Query.Direction.DESCENDING)
                    .get()
                    .await()
                    .toObjects(Treino::class.java)

                Result.success(snapshot)
            }  catch (e: Exception) {
                Result.failure(e)
            }
        }

}