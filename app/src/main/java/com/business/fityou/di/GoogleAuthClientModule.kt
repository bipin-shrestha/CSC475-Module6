package com.business.fitrack.di

import com.business.fitrack.data.repository.GoogleAuthClientImpl
import com.business.fitrack.domain.GoogleAuthClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GoogleAuthClientModule {

    @Binds
    @Singleton
    abstract fun bindGoogleAuthClient(impl: GoogleAuthClientImpl): GoogleAuthClient

}