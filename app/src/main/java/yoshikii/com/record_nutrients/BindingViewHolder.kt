package yoshikii.com.record_nutrients

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


/** DataBindingに対応した [RecyclerView.ViewHolder] */
abstract class BindingViewHolder<out T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root) {

    /** [layoutId]から ViewHolderの Viewを生成するセカンダリコンストラクタ */
    constructor(
            /** [RecyclerView.Adapter.onCreateViewHolder]の第一引数 */
            parent: ViewGroup,
            /** レイアウトのリソースID */
            @LayoutRes layoutId: Int
    ) : this(DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false))
}