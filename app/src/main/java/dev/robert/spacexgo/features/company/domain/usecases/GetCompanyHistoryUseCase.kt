package dev.robert.spacexgo.features.company.domain.usecases

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.company.domain.model.History
import dev.robert.spacexgo.features.company.domain.repository.CompanyInfoRepository
import kotlinx.coroutines.flow.Flow

class GetCompanyHistoryUseCase(private val repository: CompanyInfoRepository) {

     operator fun invoke() : Flow<Resource<List<History>>>{
        return repository.getCompanyHistory()
     }
}
