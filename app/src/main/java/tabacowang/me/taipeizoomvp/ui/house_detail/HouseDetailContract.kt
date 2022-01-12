package tabacowang.me.taipeizoomvp.ui.house_detail

import tabacowang.me.taipeizoomvp.api.model.PlantData
import tabacowang.me.taipeizoomvp.base.BasePresenter
import tabacowang.me.taipeizoomvp.base.BaseView

interface HouseDetailContract {

    interface View {
        var isActive: Boolean
        fun setLoadingIndicator(active: Boolean)
        fun showHouseDetailInfo(list: List<PlantData>)
        fun showLoadingHouseDetailError()
        fun showLoadingHouseDetailSuccess()
    }

    interface Presenter: BasePresenter {
        fun loadPlantList(houseName: String)
    }

}