package com.vitorsousa.gymmanager.data.repository

import com.google.firebase.firestore.*
import com.vitorsousa.gymmanager.core.Constants.EXERCICIOS
import com.vitorsousa.gymmanager.core.Constants.TREINOS
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.repositories.ExercicioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ExercicioRepositoryImpl @Inject constructor(
    @Named(TREINOS) private val treinosRef: CollectionReference?
): ExercicioRepository {

    override suspend fun addExercicio(exercicio: Exercicio, treinoId: String): Result<Exercicio> =
        withContext(Dispatchers.IO) {
            try {
                treinosRef
                    ?.document(treinoId)
                    ?.collection(EXERCICIOS)
                    ?.add(exercicio)
                return@withContext Result.success(exercicio)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
    }

    override suspend fun updateExercicio(exercicio: Exercicio, treinoId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExercicio(exercicioId: String, treinoId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                treinosRef
                    ?.document(treinoId)
                    ?.collection(EXERCICIOS)
                    ?.document(exercicioId)
                    ?.delete()
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }

    }

    override suspend fun getAllExercicios(listenerRegistration: EventListener<QuerySnapshot>, treinoId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                treinosRef
                    ?.document(treinoId)
                    ?.collection(EXERCICIOS)
                    ?.orderBy("nome", Query.Direction.ASCENDING)
                    ?.addSnapshotListener(listenerRegistration)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }



}