package edu.nku.classapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.nku.classapp.data.repository.StockRepository
import edu.nku.classapp.data.repository.StockRepositoryReal
import edu.nku.classapp.data.repository.UserRepository
import edu.nku.classapp.data.repository.UserRepositoryReal
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryReal: StockRepositoryReal
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryReal: UserRepositoryReal
    ): UserRepository
}