package dev.robert.spacexgo.features.company.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CompanyInfoRepositoryImpl(
    private val dao: CompanyInfoDao,
    private val apiService: ApiService
    ) : CompanyInfoRepository {

    override fun getCompanyInfo(): Flow<Resource<CompanyInfo>> = flow {

    }.flowOn(Dispatchers.IO)
}