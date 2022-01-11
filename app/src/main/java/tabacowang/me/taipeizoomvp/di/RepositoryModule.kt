package tabacowang.me.taipeizoomvp.di

import org.koin.dsl.module
import tabacowang.me.taipeizoomvp.api.repository.HouseRepository
import tabacowang.me.taipeizoomvp.api.repository.HouseRepositoryImpl
import tabacowang.me.taipeizoomvp.api.repository.PlantRepository
import tabacowang.me.taipeizoomvp.api.repository.PlantRepositoryImpl

val repositoryModule = module {
    single<HouseRepository> {
        HouseRepositoryImpl(get())
    }

    single<PlantRepository> {
        PlantRepositoryImpl(get())
    }
}