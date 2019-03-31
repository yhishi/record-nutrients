package yoshikii.com.record_nutrients

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import yoshikii.com.record_nutrients.databinding.ViewItemListBinding

class NutrientAdapter : ListAdapter<Any, BindingViewHolder<*>>(
    object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    }
) {
    init {
        setResponseData()
    }

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
                holder.bind()
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
    fun setResponseData() {
        submitList(prepareData())
    }

    /** レスポンスデータの整形 */
    private fun prepareData(): MutableList<Any> {
        val list = mutableListOf<Any>()

        list.add(TestData())
        return list
    }

    private inner class ItemViewHolder(parent: ViewGroup) :
        BindingViewHolder<ViewItemListBinding>(parent, R.layout.view_item_list) {
        fun bind() {
        }
    }


    private class TestData

    /** 栄養素見出しヘッダ */
    private data class HeaderData(
        /** 年月 */
        val yearMonth: String
    )

//    /** 年月ヘッダ */
//    private data class MonthHeaderData(
//            /** 年月 */
//            val  yearMonth: String
//    )

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