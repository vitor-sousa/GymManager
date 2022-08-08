package com.vitorsousa.gymmanager.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.vitorsousa.gymmanager.core.Constants.TREINOS
import com.vitorsousa.gymmanager.domain.models.Treino
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class TreinoRepositoryImpl @Inject constructor(
    @Named(TREINOS) private val treinoRef: CollectionReference?,
): TreinoRepository {

    override suspend fun addTreino(treino: Treino): Result<Treino> =
        withContext(Dispatchers.IO) {
            try {
                treinoRef
                    ?.add(treino)
                return@withContext Result.success(treino)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
    }

    override suspend fun updateTreino(treino: Treino): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                treinoRef
                    ?.document(treino.treinoId)
                    ?.set(treino)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
    }

    override suspend fun deleteTreino(treinoId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                treinoRef
                    ?.document(treinoId)
                    ?.delete()
                Result.success(Unit)
            }  catch (e: Exception) {
                Result.failure(e)
            }
        }


    override suspend fun getAllTreinos(listenerRegistration: EventListener<QuerySnapshot>): Result<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                treinoRef
                    ?.addSnapshotListener (listenerRegistration)
                Result.success(Unit)
            }  catch (e: Exception) {
                Result.failure(e)
            }
        }

}