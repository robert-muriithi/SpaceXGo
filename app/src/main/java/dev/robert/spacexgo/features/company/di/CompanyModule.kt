package dev.robert.spacexgo.features.company.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.data.local.dao.HistoryDao
import dev.robert.spacexgo.features.company.data.repository.CompanyInfoRepositoryImpl
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import dev.robert.spacexgo.features.company.domain.usecases.GetCompanyHistoryUseCase
import dev.robert.spacexgo.features.company.domain.usecases.GetCompanyInfoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompanyModule {

    @Provides
    @Singleton
    fun provideCompanyRepository(
        companyInfoDao: CompanyInfoDao,
        apiService: ApiService,
        historyDao: HistoryDao,
    ): CompanyInfoRepository {
        return CompanyInfoRepositoryImpl(
            companyInfoDao,
            apiService,
            historyDao
        )
    }

    @Provides
    @Singleton
    fun provideGetCompanyInfoEntity(
        companyInfoRepository: CompanyInfoRepository,
    ): GetCompanyInfoUseCase {
        return GetCompanyInfoUseCase(
            companyInfoRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetCompanyHistoryEntity(
        companyInfoRepository: CompanyInfoRepository,
    ): GetCompanyHistoryUseCase {
        return GetCompanyHistoryUseCase(
            companyInfoRepository
        )
    }

}