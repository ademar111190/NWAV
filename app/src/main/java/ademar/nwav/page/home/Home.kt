package ademar.nwav.page.home

interface Home {

    interface View {
    }

    sealed class Command {

        data class ChangeMatch(
            val value: Int,
        )

        data class ChangeMismatch(
            val value: Int,
        )

        data class ChangeGap(
            val value: Int,
        )

    }

    data class State(
        val match: Int,
        val mismatch: Int,
        val gap: Int,
    )

}
