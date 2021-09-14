package ademar.nwav.page.home

import ademar.nwav.R
import ademar.nwav.page.changeoption.ChangeOption
import ademar.nwav.page.changeoption.ChangeOptionRange
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(), Home.View {

    private lateinit var optionMatch: Button
    private lateinit var optionMismatch: Button
    private lateinit var optionGap: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        optionMatch = view.findViewById(R.id.option_match)
        optionMismatch = view.findViewById(R.id.option_mismatch)
        optionGap = view.findViewById(R.id.option_gap)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        optionMatch.setOnClickListener {
            ChangeOption(
                context = view.context,
                title = R.string.change_match_title,
                range = ChangeOptionRange(
                    min = -10,
                    max = 10,
                    current = 0,
                ),
                callback = {
                    Home.Command.ChangeMatch(it)
                },
            ).show(view)
        }
        optionMismatch.setOnClickListener {
            ChangeOption(
                context = view.context,
                title = R.string.change_mismatch_title,
                range = ChangeOptionRange(
                    min = -10,
                    max = 10,
                    current = 0,
                ),
                callback = {
                    Home.Command.ChangeMatch(it)
                },
            ).show(view)
        }
        optionGap.setOnClickListener {
            ChangeOption(
                context = view.context,
                title = R.string.change_gap_title,
                range = ChangeOptionRange(
                    min = -10,
                    max = 10,
                    current = 0,
                ),
                callback = {
                    Home.Command.ChangeMatch(it)
                },
            ).show(view)
        }
    }

}
