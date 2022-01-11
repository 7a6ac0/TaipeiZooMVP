package tabacowang.me.taipeizoomvp.api.repository

import io.reactivex.Observable
import tabacowang.me.taipeizoomvp.api.model.PlantResponse
import tabacowang.me.taipeizoomvp.api.network.ApiService

interface PlantRepository {
    fun getPlantList(): Observable<PlantResponse>
}

class PlantRepositoryImpl(
    private val apiService: ApiService
) : PlantRepository {
    override fun getPlantList() = apiService.getPlantList()
}