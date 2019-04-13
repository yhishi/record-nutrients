package yoshikii.com.record_nutrients

import android.content.Context
import android.databinding.ObservableArrayList
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import android.widget.ArrayAdapter
import yoshikii.com.record_nutrients.data.Nutrient
import yoshikii.com.record_nutrients.databinding.ViewItemListBinding

class NutrientAdapter(
    private var context: Context

) : ListAdapter<Any, BindingViewHolder<*>>(
    object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return when (viewType) {
            R.layout.view_item_list -> {
                ItemViewHolder(parent)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        when {
            holder is ItemViewHolder && item is TestData -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TestData -> R.layout.view_item_list
            else -> throw AssertionError()
        }
    }

    /** RecyclerViewの更新 */
    fun updateData(spinnerItems: ObservableArrayList<Nutrient>) {
        submitList(prepareData(spinnerItems))
    }

    /** レスポンスデータの整形 */
    private fun prepareData(spinnerItems: ObservableArrayList<Nutrient>): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(TestData(spinnerItems))
        return list
    }

    private inner class ItemViewHolder(parent: ViewGroup) :
        BindingViewHolder<ViewItemListBinding>(parent, R.layout.view_item_list) {
        fun bind(data: TestData) {

            // スピナーリスト作成
            val proteinSpinner = arrayListOf<String>()
            val carbohydrateSpinner = arrayListOf<String>()
            data.spinnerItems.forEach { spinner ->
                spinner.proteinSpinner.forEach {
                    proteinSpinner.add("${it.item} ${it.value} g")
                }
                spinner.sugarSpinner.forEach {
                    carbohydrateSpinner.add("${it.item} ${it.value} g")
                }
            }

            val proteinAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                proteinSpinner
            )

            val carbohydrateAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                carbohydrateSpinner
            )
            binding.proteinAdapter = proteinAdapter
            binding.carbohydrateAdapter = carbohydrateAdapter
        }
    }


    private class TestData(
        val spinnerItems: ObservableArrayList<Nutrient>
    )

    /** 栄養素データ部 */
    private data class ItemData(
        /** 朝 タンパク質 */
        val morningProtein: String,
        /** 朝 炭水化物 */
        val morningCarbohydrate: String,
        /** 間食1 タンパク質 */
        val betweenFirstProtein: String,
        /** 間食1 炭水化物 */
        val betweenFirstCarbohydrate: String,
        /** 昼 タンパク質 */
        val lunchProtein: String,
        /** 昼 炭水化物 */
        val lunchCarbohydrate: String,
        /** 間食2 タンパク質 */
        val betweenSecondProtein: String,
        /** 間食2 炭水化物 */
        val betweenSecondCarbohydrate: String,
        /** 夜 タンパク質 */
        val dinnerProtein: String,
        /** 夜 炭水化物 */
        val dinnerCarbohydrate: String,
        /** 就寝前 タンパク質 */
        val bedProtein: String
    )
}