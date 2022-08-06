package com.vitorsousa.gymmanager.domain.repositories

import com.vitorsousa.gymmanager.domain.models.Treino

interface TreinoRepository {

    suspend fun addTreino(treino: Treino): Result<Treino>
    suspend fun deleteTreino(treinoId: String): Result<Unit>
    suspend fun getAllTreinos(): Result<List<Treino>?>
}