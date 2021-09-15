package ademar.nwav.page.home

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class HomeInteractor {

    val output: BehaviorSubject<Home.State> = BehaviorSubject.createDefault(
        Home.State(
            match = 1,
            mismatch = -1,
            gap = -1,
            min = -10,
            max = 10,
        ),
    )
    private val subs = CompositeDisposable()

    fun bind(view: Home.View) {
        subs.addAll(
            view.output
                .flatMapSingle(::execute)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output::onNext, output::onError),
        )
    }

    fun unbind() {
        subs.clear()
    }

    private fun execute(command: Home.Command): Single<Home.State> {
        val current = output.value ?: return Single.error(NoSuchElementException())
        return Single.just(current)
            .map { state ->
                map(state, command)
            }
    }

    private fun map(state: Home.State, command: Home.Command): Home.State {
        return when (command) {
            is Home.Command.ChangeGap -> state.copy(gap = command.value)
            is Home.Command.ChangeMatch -> state.copy(match = command.value)
            is Home.Command.ChangeMismatch -> state.copy(mismatch = command.value)
            Home.Command.Initial -> state
        }
    }

}
