package ademar.nwav.page.home

import ademar.nwav.R
import ademar.nwav.page.changeoption.ChangeOptionRange
import android.content.Context
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class HomePresenter(
    private val context: Context,
    interactor: HomeInteractor,
) {

    val output: Subject<Home.ViewModel> = BehaviorSubject.create()
    private val subs = CompositeDisposable()

    init {
        subs.addAll(
            interactor.output
                .map(::map)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output::onNext, output::onError),
        )
    }

    private fun map(state: Home.State): Home.ViewModel {
        return Home.ViewModel(
            match = Home.ViewModelOptionItem(
                button = context.getString(R.string.option_match, state.match),
                dialogTitle = context.getString(R.string.change_match_title),
                range = ChangeOptionRange(
                    min = state.min,
                    max = state.max,
                    current = state.match,
                ),
            ),
            mismatch = Home.ViewModelOptionItem(
                button = context.getString(R.string.option_mismatch, state.mismatch),
                dialogTitle = context.getString(R.string.change_mismatch_title),
                range = ChangeOptionRange(
                    min = state.min,
                    max = state.max,
                    current = state.mismatch,
                ),
            ),
            gap = Home.ViewModelOptionItem(
                button = context.getString(R.string.option_gap, state.gap),
                dialogTitle = context.getString(R.string.change_gap_title),
                range = ChangeOptionRange(
                    min = state.min,
                    max = state.max,
                    current = state.gap,
                ),
            ),
        )
    }

}
