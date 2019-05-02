package yoshikii.com.record_nutrients.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.viewModel.NutrientViewModel

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nutrient, container, false)
        binding.apply {
            // NutrientViewModelのデータにセット
            nutrientViewModel.setMealData()
            adapter = NutrientAdapter(nutrientViewModel.dayMealData)
            recyclerView.adapter = adapter

            addButton.clicks {
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
            }
            return root
        }
    }

    companion object {
        const val TAG_NUTRIENT_LIST_FRAGMENT = "NutrientListFragment"
    }
}