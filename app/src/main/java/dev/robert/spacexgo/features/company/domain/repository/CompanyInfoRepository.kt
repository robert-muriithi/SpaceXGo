package dev.robert.spacexgo.features.company.domain.repository

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.model.History
import kotlinx.coroutines.flow.Flow

interface CompanyInfoRepository {
     fun getCompanyInfo() : Flow<Resource<CompanyInfo>>
     fun getCompanyHistory() : Flow<Resource<List<History>>>
}