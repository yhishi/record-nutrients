package yoshikii.com.record_nutrients.viewModel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import yoshikii.com.record_nutrients.repository.MealRepository
import yoshikii.com.record_nutrients.repository.MealRepository.updateDayMeal
import yoshikii.com.record_nutrients.repository.model.Meal
import java.util.*


class NutrientViewModel : ViewModel() {
    var dayMealData: ObservableArrayList<Meal> = ObservableArrayList()

    fun setMealData() {
        dayMealData = MealRepository.getDayMeal(Date())
    }

    fun updateMealData(meal: Meal) {
        updateDayMeal(meal)
        setMealData()
    }
}