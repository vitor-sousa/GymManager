package com.vitorsousa.gymmanager.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vitorsousa.gymmanager.core.Constants.TREINOS
import com.vitorsousa.gymmanager.core.Constants.USERS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun provideFirebaseStorageRef(storage: FirebaseStorage): StorageReference = storage.getReference("images/")

    @Provides
    @Named(USERS)
    fun provideUserRef(db: FirebaseFirestore): CollectionReference = db.collection(USERS)

    @Provides
    @Named(TREINOS)
    fun provideTreinoRef(@Named(USERS) userRef: CollectionReference, auth: FirebaseAuth): CollectionReference? =
        auth.currentUser?.let {
            userRef.document(it.uid).collection(TREINOS)
        }


}