package yoshikii.com.record_nutrients.viewModel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import yoshikii.com.record_nutrients.repository.MealRepository
import yoshikii.com.record_nutrients.repository.model.Meal


class MealViewModel : ViewModel() {
    var date: String = ""
    var dayMealData: ObservableArrayList<Meal> = ObservableArrayList()

    fun setMealData(calendarDate: String) {
        date = calendarDate
        dayMealData = MealRepository.getDayMeal(date)
    }

    fun updateMealData(meal: Meal) {
        meal.id = dayMealData.size + 1
        MealRepository.updateDayMeal(date, meal)
        setMealData(date)
    }
}