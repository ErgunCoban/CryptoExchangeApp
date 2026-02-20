package com.erguncoban.cryptoexchangeapp.di

import com.erguncoban.cryptoexchangeapp.data.datasource.UserRemoteDataSource
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(firestore: FirebaseFirestore) : UserRemoteDataSource{
        return UserRemoteDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource) : UserRepository{
        return UserRepository(userRemoteDataSource)
    }

}