package ademar.nwav.page.home

import ademar.nwav.R
import ademar.nwav.page.changeoption.ChangeOption
import ademar.nwav.page.home.Home.Command.ChangeGap
import ademar.nwav.page.home.Home.Command.ChangeMatch
import ademar.nwav.page.home.Home.Command.ChangeMismatch
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class HomeFragment : Fragment(), Home.View {

    private lateinit var optionMatch: Button
    private lateinit var optionMismatch: Button
    private lateinit var optionGap: Button

    private val subs = CompositeDisposable()
    private val interactor = HomeInteractor()
    private val presenter by lazy { HomePresenter(requireContext(), interactor) }

    override val output: Subject<Home.Command> = BehaviorSubject.createDefault(Home.Command.Initial)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        optionMatch = view.findViewById(R.id.option_match)
        optionMismatch = view.findViewById(R.id.option_mismatch)
        optionGap = view.findViewById(R.id.option_gap)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subs.addAll(
            presenter.output.subscribe(::render) { error ->
                Log.e("HOME", error.message ?: "Unknown error", error)
            },
        )
        interactor.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        interactor.unbind()
        subs.clear()
    }

    private fun render(model: Home.ViewModel) {
        val view = view ?: return

        for ((button, item, callback) in arrayOf(
            Helper(optionMatch, model.match) { output.onNext(ChangeMatch(it)) },
            Helper(optionMismatch, model.mismatch) { output.onNext(ChangeMismatch(it)) },
            Helper(optionGap, model.gap) { output.onNext(ChangeGap(it)) },
        )) {
            button.text = item.button
            button.setOnClickListener {
                ChangeOption(
                    context = view.context,
                    title = item.dialogTitle,
                    range = item.range,
                    callback = callback,
                ).show(view)
            }
        }
    }

    private data class Helper(
        val button: Button,
        val item: Home.ViewModelOptionItem,
        val callback: (Int) -> Unit,
    )

}
