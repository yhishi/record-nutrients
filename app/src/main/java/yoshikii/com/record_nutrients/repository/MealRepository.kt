package yoshikii.com.record_nutrients.repository

import android.databinding.ObservableArrayList
import io.realm.Realm
import io.realm.RealmList
import yoshikii.com.record_nutrients.repository.model.MeaLRealm
import yoshikii.com.record_nutrients.repository.model.Meal

class MealRepository {
    private var realm: Realm = Realm.getDefaultInstance()

    fun getDayMeal(date: String): ObservableArrayList<Meal> {
        val data = ObservableArrayList<Meal>()
        val allData = realm.where(MeaLRealm::class.java)
            .equalTo("date", date)
            .findAll()
        allData.forEach {
            it.mealList.forEach { meal ->
                data.add(meal)
            }
        }
        return data
    }

    fun addDayMeal(date: String, data: Meal) {
        val meal: RealmList<Meal> = RealmList()
        meal.add(
            Meal(
                id = data.id,
                time = data.time,
                item = data.item,
                amount = data.amount,
                nutrient = data.nutrient,
                memo = data.memo
            )
        )
        val ccc = MeaLRealm(
            date = date,
            mealList = meal
        )

        realm.executeTransaction {
            it.insertOrUpdate(ccc)
        }
    }

    fun editDayMeal(date: String, data: Meal) {
        realm.executeTransaction {
            val mealRealm = realm.where(MeaLRealm::class.java)
                .equalTo("date", date)
                .findAll()
            mealRealm.forEach {
                it.mealList.forEach { realm ->
                    if (realm.id == data.id) {
                        realm.time = data.time
                        realm.item = data.item
                        realm.amount = data.amount
                        realm.nutrient = data.nutrient
                        realm.memo = data.memo
                    }
                }
            }
        }
    }
}