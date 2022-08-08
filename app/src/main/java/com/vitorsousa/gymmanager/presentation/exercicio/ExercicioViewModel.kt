package com.vitorsousa.gymmanager.presentation.exercicio

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    private val exercicioRepository: ExercicioRepository,
    state: SavedStateHandle
): ViewModel(), EventListener<QuerySnapshot> {

    private var treinoId: String? = state["treinoId"]

    var exercicios = MutableLiveData<List<Exercicio>>()
        private set

    var exerciciosStatus = MutableLiveData<DataState>()
        private set

    var exercicio = Exercicio()

    var saveStatus = SingleLiveData<DataState>()
        private set

    init {
        getAllExercicios()
    }


    private fun getAllExercicios() = viewModelScope.launch {
        treinoId?.let {
            exerciciosStatus.value = DataState.LOADING
            exercicioRepository.getAllExercicios(this@ExercicioViewModel, it).fold(
                onSuccess = {
                    exerciciosStatus.value = DataState.SUCCESS
                },
                onFailure = {
                    exerciciosStatus.value = DataState.ERROR
                    println("error: ${it.message}")
                }
            )
        }
    }

    fun saveExercicio(uri: Uri?) = viewModelScope.launch {
        saveStatus.value = DataState.LOADING
        treinoId?.let {
            exercicioRepository.addExercicio(exercicio, it, uri).fold(
                onSuccess = {
                    saveStatus.value = DataState.SUCCESS
                },
                onFailure = {
                    saveStatus.value = DataState.ERROR
                    println("error: ${it.message}")
                }
            )
        }
    }

    fun deleteExercicio(exercicioId: String) = viewModelScope.launch {
        exerciciosStatus.value = DataState.LOADING
        treinoId?.let {
            exercicioRepository.deleteExercicio(exercicioId, it).fold(
                onSuccess = {
                    exerciciosStatus.value = DataState.SUCCESS
                },
                onFailure = {
                    exerciciosStatus.value = DataState.ERROR
                }
            )
        }
    }



    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        exercicios.value = value?.toObjects(Exercicio::class.java)
    }

}