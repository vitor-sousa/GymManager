package com.vitorsousa.gymmanager.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vitorsousa.gymmanager.core.Constants.TREINO
import com.vitorsousa.gymmanager.core.Constants.USER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideCurrentUser(auth: FirebaseAuth): FirebaseUser? = auth.currentUser

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideTreinoRef(db: FirebaseFirestore) = db.collection(TREINO)

//    @Provides
//    fun provideUserRef(db: FirebaseFirestore) = db.collection(USER)

}