package com.vitorsousa.gymmanager.data.repository

import android.util.Log
import com.google.firebase.firestore.*
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

    override suspend fun deleteTreino(treinoId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                treinoRef
                    .document(treinoId)
                    .delete()
                    .await()
                Result.success(Unit)
            }  catch (e: Exception) {
                Result.failure(e)
            }
        }


    override suspend fun getAllTreinos(listenerRegistration: EventListener<QuerySnapshot>): Result<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                treinoRef
                    .orderBy("data", Query.Direction.DESCENDING)
                    .addSnapshotListener (listenerRegistration)
                Result.success(Unit)
            }  catch (e: Exception) {
                Result.failure(e)
            }
        }

}