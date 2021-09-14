package ademar.nwav.page.changeoption

import ademar.nwav.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class ChangeOption(
    private val context: Context,
    @StringRes private val title: Int,
    private val range: ChangeOptionRange,
    private val callback: (Int) -> Unit,
) {

    fun show(rootView: View) {
        val contentView = rootView.findViewById<ViewGroup>(android.R.id.content)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_option_value, contentView, false)
        dialog(dialogView, numberPicker(dialogView)).show()
    }

    private fun dialog(dialogView: View, numberPicker: NumberPicker): AlertDialog {
        return AlertDialog.Builder(context, R.style.ChangeOptionValueDialogStyle)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton(R.string.change_option_apply) { _, _ ->
                callback(numberPicker.value + range.min)
            }
            .setNegativeButton(R.string.change_option_cancel) { _, _ ->
            }
            .create()
    }

    private fun numberPicker(dialogView: View): NumberPicker {
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.number_picker)
        numberPicker.minValue = 0
        numberPicker.maxValue = range.max - range.min
        numberPicker.value = range.current - range.min
        numberPicker.setFormatter {
            return@setFormatter "${it + range.min}"
        }
        return numberPicker
    }

}
