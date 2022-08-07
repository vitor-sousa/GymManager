package com.vitorsousa.gymmanager.domain.repositories

import android.net.Uri
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.vitorsousa.gymmanager.domain.models.Exercicio

interface ExercicioRepository {
    suspend fun addExercicio(exercicio: Exercicio, treinoId: String, uri: Uri?): Result<Exercicio>
    suspend fun updateExercicio(exercicio: Exercicio, treinoId: String): Result<Unit>
    suspend fun deleteExercicio(exercicioId: String, treinoId: String): Result<Unit>
    suspend fun getAllExercicios(listenerRegistration: EventListener<QuerySnapshot>, treinoId: String): Result<Unit>
}