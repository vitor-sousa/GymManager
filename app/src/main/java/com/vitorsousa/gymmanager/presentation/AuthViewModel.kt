package com.vitorsousa.gymmanager.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vitorsousa.gymmanager.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    auth: FirebaseAuth,
    private val userRepository: UserRepository
): ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser


    init {
        _currentUser.value = auth.currentUser
    }

    fun saveUser(id: String, nome: String, email: String) = viewModelScope.launch {
        userRepository.saveUser(
            id = id,
            nome = nome,
            email = email
        )
    }


    fun userSignOut() {
        AuthUI.getInstance().signOut(application).addOnCompleteListener {
            _currentUser.value = null
        }
    }
}