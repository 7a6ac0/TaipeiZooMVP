package tabacowang.me.taipeizoomvp.ui.house

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import tabacowang.me.taipeizoomvp.api.repository.HouseRepository
import tabacowang.me.taipeizoomvp.util.addDisposable

class HousePresenter(private val view: HouseContract.View): HouseContract.Presenter {
    init {
        view.presenter = this
    }

    private var compositeDisposable = CompositeDisposable()
    private val houseRepository: HouseRepository by inject(HouseRepository::class.java)

    override fun start() {
        loadHouseList()
    }

    override fun destroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun loadHouseList() {
        view.setLoadingIndicator(true)

        houseRepository.getHouseList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.result?.results ?: emptyList() }
            .subscribe ({
                if (view.isActive) {
                    view.showHouseList(it)
                    view.setLoadingIndicator(false)
                    view.showLoadingHouseSuccess()
                }
            }, {
                it.printStackTrace()
                view.setLoadingIndicator(false)
                view.showLoadingHouseError()
            })
            .addDisposable(compositeDisposable)
    }
}