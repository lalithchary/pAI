package com.pai.personalai.di

import com.pai.personalai.data.repository.ChatRepositoryImpl
import com.pai.personalai.data.repository.ConfigRepositoryImpl
import com.pai.personalai.domain.repository.ChatRepository
import com.pai.personalai.domain.repository.ConfigRepository
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
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindConfigRepository(
        configRepositoryImpl: ConfigRepositoryImpl
    ): ConfigRepository
}
