package yoshikii.com.record_nutrients.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*


class CalendarFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnDateSelectedListener {
        /** 選択した年月日を返す */
        fun onSelected(year: Int, month: Int, day: Int)
    }

    private lateinit var listener: OnDateSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDateSelectedListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()

        val dialog =
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        // キャンセルボタンなし
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "") { _, _ -> }

        // キャンセル不可
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        // monthは(0-11)のため、1を加算
        listener.onSelected(year, month + 1, day)
    }

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
        const val TAG = "CalendarFragment"
    }
}