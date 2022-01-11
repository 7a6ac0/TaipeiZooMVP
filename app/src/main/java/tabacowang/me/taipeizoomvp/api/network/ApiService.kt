package tabacowang.me.taipeizoomvp.api.network

import io.reactivex.Observable
import retrofit2.http.GET
import tabacowang.me.taipeizoomvp.api.model.HouseResponse
import tabacowang.me.taipeizoomvp.api.model.PlantResponse

interface ApiService {
    @GET("api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    fun getHouseList(): Observable<HouseResponse>

    @GET("api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    fun getPlantList(): Observable<PlantResponse>
}