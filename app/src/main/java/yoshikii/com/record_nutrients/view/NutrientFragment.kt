package yoshikii.com.record_nutrients.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmList
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding
import yoshikii.com.record_nutrients.repository.model.MeaLRealm
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.viewModel.NutrientViewModel
import java.text.SimpleDateFormat
import java.util.*

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

        addTestData()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nutrient, container, false)
        binding.apply {
            // NutrientViewModelのデータにセット
            nutrientViewModel.setMealData()
            adapter = NutrientAdapter(nutrientViewModel.dayMealData)
            recyclerView.adapter = adapter

            addButton.clicks {
                addTestData()

                // NutrientViewModelのデータにセット
                nutrientViewModel.setMealData()

                // 更新
                adapter.updateData(nutrientViewModel.dayMealData)
            }
            return root
        }
    }

    private fun addTestData() {
        val realm = Realm.getDefaultInstance()

        // TODO 実装確認用にデータセット 後ほど削除
        val meal: RealmList<Meal> = RealmList()
        meal.add(
            Meal(
                time = "8:00",
                item = "ご飯",
                amount = 180,
                calorie = 300,
                memo = "いいかんじ！"
            )
        )

        val today = SimpleDateFormat("MM/dd", Locale.JAPAN).format(Date())
        val ccc = MeaLRealm(
            date = today,
            mealList = meal
        )

        realm.executeTransaction {
            it.insertOrUpdate(ccc)
        }
    }

    companion object {
        const val TAG_NUTRIENT_LIST_FRAGMENT = "NutrientListFragment"
    }
}