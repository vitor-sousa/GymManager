package com.vitorsousa.gymmanager.presentation.exercicios

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.vitorsousa.gymmanager.core.SingleLiveData
import com.vitorsousa.gymmanager.domain.models.DataState
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.repositories.ExercicioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercicioViewModel @Inject constructor(
    private val exercicioRepository: ExercicioRepository
): ViewModel(), EventListener<QuerySnapshot> {

    var exercicios = MutableLiveData<List<Exercicio>>()
        private set

    var exerciciosStatus = MutableLiveData<DataState>()
        private set

    var exercicio = Exercicio()

    var saveStatus = SingleLiveData<DataState>()
        private set



    fun getAllExercicios(treinoId: String) = viewModelScope.launch {
        exercicios.value = emptyList()
        exerciciosStatus.value = DataState.LOADING
        exercicioRepository.getAllExercicios(this@ExercicioViewModel, treinoId).fold(
            onSuccess = {
                exerciciosStatus.value = DataState.SUCCESS
            },
            onFailure = {
                exerciciosStatus.value = DataState.ERROR
                println("error: ${it.message}")
            }
        )
    }

    fun saveExercicio(treinoId: String, uri: Uri?) = viewModelScope.launch {
        saveStatus.value = DataState.LOADING
        exercicioRepository.addExercicio(exercicio, treinoId, uri).fold(
            onSuccess = {
                saveStatus.value = DataState.SUCCESS
            },
            onFailure = {
                saveStatus.value = DataState.ERROR
                println("error: ${it.message}")
            }
        )
    }

    fun updateExercicio(treinoId: String, uri: Uri?)  = viewModelScope.launch {
        saveStatus.value = DataState.LOADING
        exercicioRepository.updateExercicio(exercicio, treinoId, uri).fold(
            onSuccess = {
                saveStatus.value = DataState.SUCCESS
            },
            onFailure = {
                saveStatus.value = DataState.ERROR
                println("error: ${it.message}")
            }
        )

    }


    fun deleteExercicio(treinoId: String, exercicioId: String) = viewModelScope.launch {
        exerciciosStatus.value = DataState.LOADING
        exercicioRepository.deleteExercicio(exercicioId, treinoId).fold(
            onSuccess = {
                exerciciosStatus.value = DataState.SUCCESS
            },
            onFailure = {
                exerciciosStatus.value = DataState.ERROR
            }
        )
    }

    fun getExercicioForUpdate(exercicioId: String) {
        exercicios.value?.find { exercicio ->
            exercicio.exercicioId == exercicioId
        }?.let {
            exercicio = it
        }
    }


    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        exercicios.value = value?.toObjects(Exercicio::class.java)
    }

}