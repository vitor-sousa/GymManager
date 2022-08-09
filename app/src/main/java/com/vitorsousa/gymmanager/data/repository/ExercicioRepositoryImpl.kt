package com.vitorsousa.gymmanager.data.repository

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.vitorsousa.gymmanager.core.Constants.EXERCICIOS
import com.vitorsousa.gymmanager.core.Constants.TREINOS
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.repositories.ExercicioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ExercicioRepositoryImpl @Inject constructor(
    @Named(TREINOS) private val treinosRef: CollectionReference?,
    private val storageReference: StorageReference
): ExercicioRepository {

    override suspend fun addExercicio(exercicio: Exercicio, treinoId: String, uri: Uri?): Result<Exercicio> =
        withContext(Dispatchers.IO) {
            try {
                if(uri != null) {
                    storageReference.child("$treinoId/${exercicio.nome}").putFile(uri)
                        .addOnSuccessListener {
                            val result = it.metadata?.reference?.downloadUrl
                            result?.addOnSuccessListener { finalUri ->
                                exercicio.imagem = finalUri.toString()
                                saveExercicio(exercicio, treinoId)
                            }
                        }
                } else
                    saveExercicio(exercicio, treinoId)
                return@withContext Result.success(exercicio)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
    }

    override suspend fun updateExercicio(exercicio: Exercicio, treinoId: String, uri: Uri?): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                if(uri != null) {
                    storageReference.child("$treinoId/${exercicio.nome}").putFile(uri)
                        .addOnSuccessListener {
                            val result = it.metadata?.reference?.downloadUrl
                            result?.addOnSuccessListener { finalUri ->
                                exercicio.imagem = finalUri.toString()
                                updateExercicio(exercicio, treinoId)
                            }
                        }
                } else
                    updateExercicio(exercicio, treinoId)
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
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


    private fun saveExercicio(exercicio: Exercicio, treinoId: String) {
        treinosRef
            ?.document(treinoId)
            ?.collection(EXERCICIOS)
            ?.add(exercicio)
    }

    private fun updateExercicio(exercicio: Exercicio, treinoId: String) {
        treinosRef
            ?.document(treinoId)
            ?.collection(EXERCICIOS)
            ?.document(exercicio.exercicioId)
            ?.set(exercicio)
    }


}