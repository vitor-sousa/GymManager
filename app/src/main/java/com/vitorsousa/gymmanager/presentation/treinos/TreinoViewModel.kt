package com.vitorsousa.gymmanager.presentation.treinos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.vitorsousa.gymmanager.domain.models.DataState
import com.vitorsousa.gymmanager.domain.models.Treino
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreinoViewModel @Inject constructor(
    private val treinoRepository: TreinoRepository
): ViewModel() {

    var treinosStatus = MutableLiveData<DataState>()
        private set

    var treinos = MutableLiveData<List<Treino>>()
        private set

    var nome: String = ""
    var descricao: String = ""

    var saveStatus = MutableLiveData<DataState>()
        private set


    init {
        getAllTreinos()
    }


    fun saveTreino() = viewModelScope.launch {
        val treino = Treino(
            treinoId = "",
            nome =  nome,
            descricao = descricao,
            data = Timestamp(Date()),
            exercicios = emptyList()
        )

        saveStatus.value = DataState.LOADING
        treinoRepository.addTreino(treino).fold(
            onSuccess = {
                nome = ""
                descricao = ""
                saveStatus.value = DataState.SUCCESS
            },
            onFailure = {
                saveStatus.value = DataState.ERROR
            }
        )
    }

    fun deleteTreino(treinoId: String) = viewModelScope.launch {
        treinosStatus.value = DataState.LOADING
        treinoRepository.deleteTreino(treinoId).fold(
            onSuccess = { treinosStatus.value = DataState.SUCCESS },
            onFailure = { treinosStatus.value = DataState.ERROR }
        )
    }

    private fun getAllTreinos() = viewModelScope.launch {
        treinosStatus.value = DataState.LOADING

        treinoRepository.getAllTreinos().fold(
            onSuccess = {
                treinosStatus.value = DataState.SUCCESS
                treinos.value = it
            },
            onFailure = {
                treinosStatus.value = DataState.ERROR
                println("error: ${it.message}")
            }
        )
    }
}