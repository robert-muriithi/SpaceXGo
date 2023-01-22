package dev.robert.spacexgo.features.company.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.data.local.dao.HistoryDao
import dev.robert.spacexgo.features.company.data.mappers.toCompany
import dev.robert.spacexgo.features.company.data.mappers.toCompanyEntity
import dev.robert.spacexgo.features.company.data.mappers.toHistory
import dev.robert.spacexgo.features.company.data.mappers.toHistoryEntity
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.model.History
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CompanyInfoRepositoryImpl(
    private val companyInfoDao: CompanyInfoDao,
    private val apiService: ApiService,
    private val historyDao: HistoryDao
    ) : CompanyInfoRepository {

    override fun getCompanyInfo(): Flow<Resource<CompanyInfo>> = flow {
        val infoFromDb = companyInfoDao.getCompanyInfo()
        if(infoFromDb != null){
            emit(Resource.Success(infoFromDb.toCompany()))
        }
        else{
            try {
                val remoteData = apiService.getCompanyInfo()
                companyInfoDao.deleteCompanyInfo()
                companyInfoDao.insertCompanyInfo(info = remoteData.toCompanyEntity())
                val result = companyInfoDao.getCompanyInfo()
                emit(Resource.Success(data = result.toCompany()))
            }catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }

        }
    }.flowOn(Dispatchers.IO)

    override fun getCompanyHistory(): Flow<Resource<List<History>>> = flow {
        emit(Resource.Loading())
        val localHistory = historyDao.getCompanyHistory()
        if(localHistory.isNotEmpty()){
            emit(Resource.Success(localHistory.map { it.toHistory() }))
        }
        else{
            try {
                val remoteData = apiService.getCompanyHistory()
                historyDao.deleteCompanyHistory()
                historyDao.insertCompanyHistory(remoteData.map { it.toHistoryEntity() })
                val result = historyDao.getCompanyHistory()
                emit(Resource.Success(data = result.map { it.toHistory() }))
            }catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }
        }

    }.flowOn(Dispatchers.IO)
}