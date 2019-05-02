package yoshikii.com.record_nutrients.common

import android.view.View


/** View.setOnClickListenerの拡張関数 */
inline fun View.clicks(crossinline onClick: () -> Unit) {
    setOnClickListener { onClick() }
}
