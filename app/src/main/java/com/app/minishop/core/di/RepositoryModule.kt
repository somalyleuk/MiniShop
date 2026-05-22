package com.app.minishop.core.di

import com.app.minishop.data.datasource.remote.ProductRemoteDataSource
import com.app.minishop.data.datasource.remote.ProductRemoteDataSourceImpl
import com.app.minishop.data.repository.ProductRepositoryImpl
import com.app.minishop.domain.repository.ProductRepository
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
    abstract fun bindProductRemoteDataSource(
        impl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}
