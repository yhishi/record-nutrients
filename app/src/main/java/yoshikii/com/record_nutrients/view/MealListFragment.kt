package yoshikii.com.record_nutrients.view

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding
import yoshikii.com.record_nutrients.databinding.ViewDialogAddMealBinding
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.viewModel.MealViewModel


@Suppress("DEPRECATION")
class MealListFragment : Fragment() {

    private lateinit var binding: FragmentNutrientBinding
    private lateinit var adapter: MealListAdapter
    private val nutrientViewModel by lazy {
        ViewModelProviders.of(this).get(MealViewModel::class.java)
    }
    private val selectedDate by lazy { arguments?.getString(KEY_DATE) ?: throw IllegalArgumentException() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nutrient,
            container,
            false
        )
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            // NutrientViewModelのデータにセット
            nutrientViewModel.setMealData(selectedDate)
            adapter = MealListAdapter(nutrientViewModel.date, nutrientViewModel.dayMealData)
            recyclerView.adapter = adapter

            addButton.clicks {
                // データ追加用のダイアログ表示
                val builder = AlertDialog.Builder(requireActivity())
                val dialogBinding = DataBindingUtil.inflate<ViewDialogAddMealBinding>(
                    LayoutInflater.from(requireActivity()),
                    R.layout.view_dialog_add_meal,
                    null,
                    false
                )

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
                            addData(
                                Meal(
                                    time = "$hour:$minute",
                                    item = item.text.toString(),
                                    amount = amount.text.toString().toInt(),
                                    nutrient = nutrient.text.toString().toInt(),
                                    memo = memo.text.toString()
                                )
                            )
                            dialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.add_meal_finish_message),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun addData(meal: Meal) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            nutrientViewModel.updateMealData(meal)
            // 更新
            adapter.updateData(nutrientViewModel.dayMealData)
            progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val TAG = "MealListFragment"
        private const val KEY_DATE = "date"
        fun newInstance(name: String): MealListFragment {
            return MealListFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_DATE, name)
                }
            }
        }
    }
}