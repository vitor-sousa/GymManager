package com.vitorsousa.gymmanager.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    currentUser: FirebaseUser?
): ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser


    init {
        _currentUser.value = currentUser
    }


    fun userSignOut() {
        AuthUI.getInstance().signOut(application).addOnCompleteListener {
            _currentUser.value = null
        }
    }
}