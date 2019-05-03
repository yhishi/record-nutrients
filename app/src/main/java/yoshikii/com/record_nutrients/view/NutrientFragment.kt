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
import android.widget.Toast
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding
import yoshikii.com.record_nutrients.databinding.ViewDialogAddMealBinding
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.viewModel.NutrientViewModel


@Suppress("DEPRECATION")
class NutrientFragment : Fragment() {

    private lateinit var binding: FragmentNutrientBinding
    private lateinit var adapter: NutrientAdapter
    private val nutrientViewModel by lazy {
        ViewModelProviders.of(this).get(NutrientViewModel::class.java)
    }

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
        binding.apply {
            // NutrientViewModelのデータにセット
            nutrientViewModel.setMealData()
            adapter = NutrientAdapter(nutrientViewModel.dayMealData)
            recyclerView.adapter = adapter

            addButton.clicks {
                progressBar.visibility = View.VISIBLE
                nutrientViewModel.updateMealData(
                    Meal(
                        time = "9:00",
                        item = "ステーキ",
                        amount = 200,
                        calorie = 500
                    )
                )
                // 更新
                adapter.updateData(nutrientViewModel.dayMealData)
                progressBar.visibility = View.INVISIBLE

                val builder = AlertDialog.Builder(requireActivity())
                val dialogBinding = DataBindingUtil.inflate<ViewDialogAddMealBinding>(
                    LayoutInflater.from(requireActivity()),
                    R.layout.view_dialog_add_meal,
                    null,
                    false
                )
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
                        if (item.text.toString().isEmpty() ||
                            amount.text.toString().isEmpty() ||
                            calorie.text.toString().isEmpty()
                        ) {
                            Toast.makeText(context, "品名と量とカロリーは入力必須です。", Toast.LENGTH_LONG).show()
                        } else {
                            dialog.dismiss()
                        }
                    }
                }
            }
            return root
        }
    }

    companion object {
        const val TAG_NUTRIENT_LIST_FRAGMENT = "NutrientListFragment"
    }
}