package com.erguncoban.cryptoexchangeapp.di

import com.erguncoban.cryptoexchangeapp.data.datasource.FirebaseAuthDataSource
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth) : FirebaseAuthDataSource{
        return FirebaseAuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(authDataSource: FirebaseAuthDataSource) : FirebaseAuthRepository{
        return FirebaseAuthRepository(authDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}