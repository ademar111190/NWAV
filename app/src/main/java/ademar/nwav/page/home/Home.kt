package ademar.nwav.page.home

import ademar.nwav.page.changeoption.ChangeOptionRange
import io.reactivex.rxjava3.subjects.Subject

interface Home {

    interface View {
        val output: Subject<Command>
    }

    sealed class Command {

        object Initial : Command()

        data class ChangeMatch(
            val value: Int,
        ) : Command()

        data class ChangeMismatch(
            val value: Int,
        ) : Command()

        data class ChangeGap(
            val value: Int,
        ) : Command()

    }

    data class State(
        val match: Int,
        val mismatch: Int,
        val gap: Int,
        val min: Int,
        val max: Int,
    )

    data class ViewModel(
        val match: ViewModelOptionItem,
        val mismatch: ViewModelOptionItem,
        val gap: ViewModelOptionItem,
    )

    data class ViewModelOptionItem(
        val button: String,
        val dialogTitle: String,
        val range: ChangeOptionRange,
    )

}
