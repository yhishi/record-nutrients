package yoshikii.com.record_nutrients.repository

import android.databinding.ObservableArrayList
import io.realm.Realm
import yoshikii.com.record_nutrients.repository.model.MeaLRealm
import yoshikii.com.record_nutrients.repository.model.Meal
import java.util.*

object MealRepository {

    fun getDayMeal(date: Date): ObservableArrayList<Meal> {
        val data = ObservableArrayList<Meal>()
        val realm = Realm.getDefaultInstance()
        val allData = realm.where(MeaLRealm::class.java).findAll()
        allData.forEach {
            it.mealList.forEach { meal ->
                data.add(meal)
            }
        }
        return data
    }
}

