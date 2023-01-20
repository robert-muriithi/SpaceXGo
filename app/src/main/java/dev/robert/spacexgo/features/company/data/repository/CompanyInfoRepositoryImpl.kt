package dev.robert.spacexgo.features.company.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.data.mappers.toCompany
import dev.robert.spacexgo.features.company.data.mappers.toCompanyEntity
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CompanyInfoRepositoryImpl(
    private val dao: CompanyInfoDao,
    private val apiService: ApiService
    ) : CompanyInfoRepository {

    override fun getCompanyInfo(): Flow<Resource<CompanyInfo>> = flow {
        val infoFromDb = dao.getCompanyInfo()
        if(infoFromDb != null){
            emit(Resource.Success(infoFromDb.toCompany()))
        }
        else{
            try {
                val remoteData = apiService.getCompanyInfo()
                dao.deleteCompanyInfo()
                dao.insertCompanyInfo(info = remoteData.toCompanyEntity())
                val result = dao.getCompanyInfo()
                emit(Resource.Success(data = result.toCompany()))
            }catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }

        }
    }.flowOn(Dispatchers.IO)
}