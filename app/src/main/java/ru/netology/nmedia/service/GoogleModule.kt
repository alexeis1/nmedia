package ru.netology.nmedia.service

import com.google.android.gms.common.GoogleApiAvailability
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GoogleModule {
    @Provides
    @Singleton
    fun provideGoogleServices(): GoogleApiAvailability = GoogleApiAvailability.getInstance()
}