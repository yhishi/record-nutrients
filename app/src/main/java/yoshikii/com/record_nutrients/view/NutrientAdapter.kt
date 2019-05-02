package yoshikii.com.record_nutrients.view

import android.databinding.ObservableArrayList
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.common.BindingViewHolder
import yoshikii.com.record_nutrients.databinding.ViewItemListBinding
import yoshikii.com.record_nutrients.repository.model.Meal

class NutrientAdapter(
    data: ObservableArrayList<Meal>
) : ListAdapter<Meal, BindingViewHolder<*>>(
    object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
    }
) {
    init {
        updateData(data)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Meal -> R.layout.view_item_list
            else -> throw AssertionError()
        }
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
            holder is ItemViewHolder && item is Meal -> {
                holder.bind(item)
            }
        }
    }

    /** RecyclerViewの更新 */
    fun updateData(data: ObservableArrayList<Meal>) {
        submitList(data.toList())
    }

    private inner class ItemViewHolder(parent: ViewGroup) :
        BindingViewHolder<ViewItemListBinding>(parent, R.layout.view_item_list) {
        fun bind(data: Meal) {
            binding.apply {
                mealData = data
            }
        }
    }
}