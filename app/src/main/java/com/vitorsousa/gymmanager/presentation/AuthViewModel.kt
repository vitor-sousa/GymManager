package com.vitorsousa.gymmanager.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser


    init {
        _currentUser.value = auth.currentUser
    }


    fun userSignOut() {
        AuthUI.getInstance().signOut(application).addOnCompleteListener {
            _currentUser.value = null
        }
    }
}