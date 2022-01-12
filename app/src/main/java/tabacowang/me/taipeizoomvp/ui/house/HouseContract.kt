package tabacowang.me.taipeizoomvp.ui.house

import tabacowang.me.taipeizoomvp.api.model.HouseData
import tabacowang.me.taipeizoomvp.base.BasePresenter
import tabacowang.me.taipeizoomvp.base.BaseView

interface HouseContract {

    interface View {
        var isActive: Boolean
        fun setLoadingIndicator(active: Boolean)
        fun showHouseList(list: List<HouseData>)
        fun showLoadingHouseError()
        fun showLoadingHouseSuccess()
        fun showHouseDetail(houseData: HouseData)
    }

    interface Presenter: BasePresenter {
        fun loadHouseList()
    }
}