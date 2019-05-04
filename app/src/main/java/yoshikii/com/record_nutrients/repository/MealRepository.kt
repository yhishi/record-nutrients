package yoshikii.com.record_nutrients.repository

import android.databinding.ObservableArrayList
import io.realm.Realm
import io.realm.RealmList
import yoshikii.com.record_nutrients.repository.model.MeaLRealm
import yoshikii.com.record_nutrients.repository.model.Meal

object MealRepository {

    fun getDayMeal(date: String): ObservableArrayList<Meal> {
        val data = ObservableArrayList<Meal>()
        val realm = Realm.getDefaultInstance()
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

    fun updateDayMeal(date: String, data: Meal) {
        val realm = Realm.getDefaultInstance()
        val meal: RealmList<Meal> = RealmList()
        meal.add(
            Meal(
                time = data.time,
                item = data.item,
                amount = data.amount,
                calorie = data.calorie,
                memo = data.memo
            )
        )
        //val today = SimpleDateFormat("MM/dd", Locale.JAPAN).format(Date())
        val ccc = MeaLRealm(
            date = date,
            mealList = meal
        )

        realm.executeTransaction {
            it.insertOrUpdate(ccc)
        }
    }
}

