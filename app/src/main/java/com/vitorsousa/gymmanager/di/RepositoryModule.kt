package com.vitorsousa.gymmanager.di

import com.vitorsousa.gymmanager.data.repository.TreinoRepositoryImpl
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsTreinoRepository(
        treinoRepositoryImpl: TreinoRepositoryImpl
    ): TreinoRepository
}