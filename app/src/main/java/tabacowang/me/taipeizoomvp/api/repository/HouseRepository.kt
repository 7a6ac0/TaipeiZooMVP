package tabacowang.me.taipeizoomvp.api.repository

import io.reactivex.Observable
import tabacowang.me.taipeizoomvp.api.model.HouseResponse
import tabacowang.me.taipeizoomvp.api.network.ApiService

interface HouseRepository {
    fun getHouseList(): Observable<HouseResponse>
}

class HouseRepositoryImpl(
    private val apiService: ApiService
) : HouseRepository {
    override fun getHouseList() = apiService.getHouseList()
}