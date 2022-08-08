package com.vitorsousa.gymmanager.presentation.treinos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.vitorsousa.gymmanager.core.SingleLiveData
import com.vitorsousa.gymmanager.domain.models.DataState
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.models.Treino
import com.vitorsousa.gymmanager.domain.repositories.ExercicioRepository
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreinoViewModel @Inject constructor(
    private val treinoRepository: TreinoRepository
): ViewModel(), EventListener<QuerySnapshot> {


    var treinosStatus = MutableLiveData<DataState>()
        private set

    var treinos = MutableLiveData<List<Treino>>()
        private set

    var treino = Treino()

    var saveStatus = SingleLiveData<DataState>()
        private set


    init {
        getAllTreinos()
    }


    fun saveTreino() = viewModelScope.launch {
        treino.data = Timestamp(Date())
        saveStatus.value = DataState.LOADING
        treinoRepository.addTreino(treino).fold(
            onSuccess = {
                treino = Treino()
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

    fun updateTreino() = viewModelScope.launch {
        treino.data = Timestamp(Date())
        saveStatus.value = DataState.LOADING
        treinoRepository.updateTreino(treino).fold(
            onSuccess = {
                treino = Treino()
                saveStatus.value = DataState.SUCCESS
            },
            onFailure = {
                saveStatus.value = DataState.ERROR
            }
        )
    }


    fun getTreinoForUpdate(position: Int) {
        treinos.value?.get(position)?.let {
            treino = it
        }
    }


    private fun getAllTreinos() = viewModelScope.launch {
        treinosStatus.value = DataState.LOADING

        treinoRepository.getAllTreinos(this@TreinoViewModel).fold(
            onSuccess = {
                treinosStatus.value = DataState.SUCCESS
            },
            onFailure = {
                treinosStatus.value = DataState.ERROR
                println("error: ${it.message}")
            }
        )
    }

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        treinos.value = value?.toObjects(Treino::class.java)
    }
}