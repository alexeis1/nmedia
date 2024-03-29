package ru.netology.nmedia.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.ui.login.RegisterApiService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideApiService(auth: AppAuth): ApiService {
        return retrofit(okhttp(loggingInterceptor(), authInterceptor(auth)), BASE_URL)
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterApiService(auth: AppAuth) : RegisterApiService {
        return retrofit(okhttp(loggingInterceptor(), authInterceptor(auth)), BASE_URL)
            .create(RegisterApiService::class.java)
    }
}