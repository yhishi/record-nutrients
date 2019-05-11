package yoshikii.com.record_nutrients.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.ViewDialogAddMealBinding
import yoshikii.com.record_nutrients.repository.model.Meal



@Suppress("DEPRECATION")
class UpdateMealFragment : DialogFragment() {

    interface OnUpdateListener {
        /** 入力情報を渡す */
        fun onUpdate(meal: Meal)
    }

    private lateinit var listener: OnUpdateListener
    private val originalId by lazy { arguments?.getInt(KEY_ID) ?: 0 }
    private val originalTime by lazy { arguments?.getString(KEY_TIME) ?: "" }
    private val originalItem by lazy { arguments?.getString(KEY_ITEM) ?: "" }
    private val originalAmount by lazy { arguments?.getInt(KEY_AMOUNT) ?: 0 }
    private val originalNutrient by lazy { arguments?.getInt(KEY_NUTRIENT) ?: 0 }
    private val originalMemo by lazy { arguments?.getString(KEY_MEMO) ?: "" }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        /** 呼び出し元のfragmentで設定したtargetFragment */
        listener = targetFragment as OnUpdateListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialogBinding = DataBindingUtil.inflate<ViewDialogAddMealBinding>(
            LayoutInflater.from(requireActivity()),
            R.layout.view_dialog_add_meal,
            null,
            false
        )

        dialogBinding.apply {
            // 食事リストの新規追加の場合
            if (originalId == 0) {
                timePicker.apply {
                    setIs24HourView(true)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = 12
                        minute = 0
                    } else {
                        currentHour = 12
                        currentMinute = 0
                    }
                }
            } else {
                // 食事リストの更新の場合、一覧に表示されている内容を一旦表示
                timePicker.apply {
                    setIs24HourView(true)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = originalTime.split(":")[0].toInt()
                        minute = originalTime.split(":")[1].toInt()
                    } else {
                        currentHour = originalTime.split(":")[0].toInt()
                        currentMinute = originalTime.split(":")[1].toInt()
                    }
                }
                item.setText(originalItem)
                amount.setText(originalAmount.toString())
                nutrient.setText(originalNutrient.toString())
                memo.setText(originalMemo)
            }
        }

        // 時刻入力用のスピナー設定
        dialogBinding.timePicker.apply {
            setIs24HourView(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = 12
                minute = 0
            } else {
                currentHour = 12
                currentMinute = 0
            }
        }

        val dialog = builder
            .setView(dialogBinding.root)
            .setPositiveButton("追加") { _, _ -> }
            .setNegativeButton("キャンセル") { _, _ -> }
            .show()

        val addButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)

        addButton.clicks {
            dialogBinding.apply {
                // 必須項目に入力がない時
                if (item.text.toString().isEmpty() ||
                    amount.text.toString().isEmpty() ||
                    nutrient.text.toString().isEmpty()
                ) {
                    // エラートースト表示
                    val toast = Toast.makeText(
                        requireContext(), getString(R.string.add_meal_error_message),
                        Toast.LENGTH_LONG
                    )

                    val toastMessage = toast.view.findViewById<View>(android.R.id.message) as TextView
                    toastMessage.setTextColor(resources.getColor(R.color.colorAccent))
                    toast.show()
                } else {
                    val (hour, minute) =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timePicker.hour to String.format("%02d", timePicker.minute)
                        } else {
                            timePicker.currentHour to String.format("%02d", timePicker.currentMinute)
                        }

                    // データ追加
                    listener.onUpdate(
                        Meal(
                            id = originalId,
                            time = "$hour:$minute",
                            item = item.text.toString(),
                            amount = amount.text.toString().toInt(),
                            nutrient = nutrient.text.toString().toInt(),
                            memo = memo.text.toString()
                        )
                    )
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_TIME = "originalTime"
        private const val KEY_ITEM = "originalItem"
        private const val KEY_AMOUNT = "originalAmount"
        private const val KEY_NUTRIENT = "originalNutrient"
        private const val KEY_MEMO = "originalMemo"
        fun newInstance(
            id: Int = 0,
            time: String = "",
            item: String = "",
            amount: Int = 0,
            nutrient: Int = 0,
            memo: String = ""
        ): UpdateMealFragment {
            return UpdateMealFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ID, id)
                    putString(KEY_TIME, time)
                    putString(KEY_ITEM, item)
                    putInt(KEY_AMOUNT, amount)
                    putInt(KEY_NUTRIENT, nutrient)
                    putString(KEY_MEMO, memo)
                }
            }
        }
        const val TAG = "UpdateMealFragment"
    }
}