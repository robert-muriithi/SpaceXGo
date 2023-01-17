package dev.robert.spacexgo.features.company.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.data.repository.CompanyInfoRepositoryImpl
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompanyModule {

    @Provides
    @Singleton
    fun provideCompanyRepository(
        dao: CompanyInfoDao,
        apiService: ApiService,
    ): CompanyInfoRepository {
        return CompanyInfoRepositoryImpl(dao, apiService)
    }
}