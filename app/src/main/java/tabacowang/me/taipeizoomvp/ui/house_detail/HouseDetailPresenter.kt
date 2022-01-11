package tabacowang.me.taipeizoomvp.ui.house_detail

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import tabacowang.me.taipeizoomvp.api.repository.PlantRepository
import tabacowang.me.taipeizoomvp.util.addDisposable

class HouseDetailPresenter(private val view: HouseDetailContract.View): HouseDetailContract.Presenter {
    init {
        view.presenter = this
    }

    private var compositeDisposable = CompositeDisposable()
    private val plantRepository: PlantRepository by inject(PlantRepository::class.java)

    override fun loadPlantList(houseName: String) {
        view.setLoadingIndicator(true)

        plantRepository.getPlantList()
            .flatMap { Observable.fromIterable(it.result?.results ?: emptyList()) }
            .filter { (it.location?.contains(houseName) ?: false) || (it.location?.equals("全園普遍分佈") ?: false) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                if (view.isActive) {
                    view.showHouseDetailInfo(it)
                    view.setLoadingIndicator(false)
                    view.showLoadingHouseDetailSuccess()
                }
            }, {
                it.printStackTrace()
                view.setLoadingIndicator(false)
                view.showLoadingHouseDetailError()
            })
            .addDisposable(compositeDisposable)
    }

    override fun start() {

    }

    override fun destroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}