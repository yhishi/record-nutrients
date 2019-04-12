package yoshikii.com.record_nutrients.data

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import io.realm.Realm
import yoshikii.com.record_nutrients.NutrientAdapter
import yoshikii.com.record_nutrients.NutrientRealm


class NutrientViewModel : ViewModel() {
    val spinnerItems: ObservableArrayList<Nutrient> = ObservableArrayList()
    val name = "hishi"

    fun getRealm(): ObservableArrayList<Nutrient>  {
        val realm = Realm.getDefaultInstance()
        val all = realm.where(NutrientRealm::class.java).findAll()

        val proteinSpinnerList: MutableList<Spinner> = mutableListOf()
        val sugarSpinnerList: MutableList<Spinner> = mutableListOf()

        all.forEach { nutrientRealm ->
            nutrientRealm.proteinSpinner.forEach {
                println(it.item)
                println(it.value)
                proteinSpinnerList += Spinner(
                    item = it.item,
                    value = it.value
                )
            }
            nutrientRealm.sugarSpinner.forEach {
                println(it.item)
                println(it.value)
                sugarSpinnerList += Spinner(
                    item = it.item,
                    value = it.value
                )
            }
        }

        spinnerItems += Nutrient(
            proteinSpinner = proteinSpinnerList,
            sugarSpinner = sugarSpinnerList
        )
        return spinnerItems
    }
}

data class Spinner(
    var id: Int? = null,
    var item: String? = null,
    var value: Int? = null
)

data class Nutrient(
    var proteinSpinner: List<Spinner>,
    var sugarSpinner: List<Spinner>
)

//@BindingAdapter("viewModels")
//fun setViewModels(recyclerView: RecyclerView, items: ObservableArrayList<Nutrient>) {
//    val adapter = recyclerView.adapter as NutrientAdapter
//    adapter.submitList(NutrientViewModel().getRealm().toList())
//}
// app:viewModels="@{viewModel.spinnerItems}"
