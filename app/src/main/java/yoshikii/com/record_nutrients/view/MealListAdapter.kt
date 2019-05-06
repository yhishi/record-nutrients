package yoshikii.com.record_nutrients.view

import android.databinding.ObservableArrayList
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.BindingViewHolder
import yoshikii.com.record_nutrients.common.clicks
import yoshikii.com.record_nutrients.databinding.ViewItemHeaderBinding
import yoshikii.com.record_nutrients.databinding.ViewItemListBinding
import yoshikii.com.record_nutrients.repository.model.Meal

class MealListAdapter(
    private val mealDate: String,
    data: ObservableArrayList<Meal>
) : ListAdapter<Any, BindingViewHolder<*>>(
    object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    }
) {
    init {
        updateData(data)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderData -> R.layout.view_item_header
            is Meal -> R.layout.view_item_list
            else -> throw AssertionError()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return when (viewType) {
            R.layout.view_item_header -> HeaderViewHolder(parent)
            R.layout.view_item_list -> ItemViewHolder(parent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        when {
            holder is ItemViewHolder && item is Meal -> {
                holder.bind(item)
            }
            holder is HeaderViewHolder && item is HeaderData -> {
                holder.bind()
            }
        }
    }

    /** RecyclerViewの更新 */
    fun updateData(data: ObservableArrayList<Meal>) {
        val list = mutableListOf<Any>()
        list.add(HeaderData())
        data.forEach { list.add(it) }
        submitList(list)
    }

    /** 日付表示用 */
    private class HeaderData

    /** 年月ヘッダを表示 */
    private inner class HeaderViewHolder(parent: ViewGroup) :
        BindingViewHolder<ViewItemHeaderBinding>(parent, R.layout.view_item_header) {

        fun bind() {
            binding.apply {
                binding.date.text = mealDate
            }
        }
    }

    /** 食べたリストを表示 */
    private inner class ItemViewHolder(parent: ViewGroup) :
        BindingViewHolder<ViewItemListBinding>(parent, R.layout.view_item_list) {
        fun bind(data: Meal) {
            binding.apply {
                mealData = data
                root.clicks {  }
            }
        }
    }
}