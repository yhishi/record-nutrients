package yoshikii.com.record_nutrients.view.record.meal

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.FragmentMealBinding
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.view.UpdateMealFragment
import yoshikii.com.record_nutrients.viewModel.MealViewModel


@Suppress("DEPRECATION")
class MealListFragment : Fragment(), UpdateMealFragment.OnUpdateListener {

    private lateinit var binding: FragmentMealBinding
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
            R.layout.fragment_meal,
            container,
            false
        )
        initView()
        return binding.root
    }

    /** [UpdateMealFragment]で入力した情報で更新 */
    override fun onUpdate(meal: Meal) {
        binding.apply {
            progressBar.visibility = View.VISIBLE

            // データ追加
            if (meal.id == 0) {
                nutrientViewModel.addMealData(meal)
                adapter.updateData(nutrientViewModel.dayMealData)
                showComplete(getString(R.string.add_meal_message))
            } else {
                // データ更新
                nutrientViewModel.editMealData(meal)
                adapter.updateData(nutrientViewModel.dayMealData)
                showComplete(getString(R.string.edit_meal_message))
            }

            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun initView() {
        binding.apply {
            // NutrientViewModelのデータにセット
            nutrientViewModel.setMealData(selectedDate)
            adapter = MealListAdapter(
                this@MealListFragment,
                fragmentManager ?: throw IllegalArgumentException(),
                nutrientViewModel.date,
                nutrientViewModel.dayMealData
            )
            recyclerView.adapter = adapter

            addButton.clicks {
                // データ追加用のダイアログ表示
                val dialog = UpdateMealFragment.newInstance()
                /** [UpdateMealFragment]から戻すためにセット */
                dialog.setTargetFragment(this@MealListFragment, 0)
                dialog.show(fragmentManager, UpdateMealFragment.TAG)
            }
        }
    }

    private fun showComplete(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
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