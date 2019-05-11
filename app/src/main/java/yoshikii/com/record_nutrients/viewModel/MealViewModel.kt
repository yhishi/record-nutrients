package yoshikii.com.record_nutrients.viewModel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import yoshikii.com.record_nutrients.repository.MealRepository
import yoshikii.com.record_nutrients.repository.model.Meal


class MealViewModel : ViewModel() {
    var date: String = ""
    var dayMealData: ObservableArrayList<Meal> = ObservableArrayList()
    private val mealRepository = MealRepository()

    fun setMealData(calendarDate: String) {
        date = calendarDate
        dayMealData = mealRepository.getDayMeal(date)
    }

    fun addMealData(meal: Meal) {
        meal.id = dayMealData.size + 1
        mealRepository.addDayMeal(date, meal)
        setMealData(date)
    }

    fun editMealData(meal: Meal) {
        mealRepository.editDayMeal(date, meal)
        setMealData(date)
    }
}