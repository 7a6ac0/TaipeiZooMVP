package tabacowang.me.taipeizoomvp.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addDisposable(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}
