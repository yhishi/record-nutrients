package yoshikii.com.record_nutrients.viewModel

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import yoshikii.com.record_nutrients.repository.MealRepository
import yoshikii.com.record_nutrients.repository.model.Meal
import yoshikii.com.record_nutrients.view.NutrientAdapter
import java.util.*


class NutrientViewModel : ViewModel() {
    val dayMealData: ObservableArrayList<Meal> =
            MealRepository
                .instance
                .getDayMeal(Date())
}

@BindingAdapter("viewModels")
fun setViewModels(recyclerView: RecyclerView, items: ObservableArrayList<Meal>) {
    recyclerView.adapter = NutrientAdapter(items)
}