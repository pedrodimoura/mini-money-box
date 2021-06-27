package com.example.minimoneybox.common.presentation.binding

import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> RecyclerView.ViewHolder.viewBinding(): ReadOnlyProperty<RecyclerView.ViewHolder, T> =
    ViewHolderBindingProperty { viewHolder -> viewHolder.itemView.bind(T::class.java) }

class ViewHolderBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (RecyclerView.ViewHolder) -> T,
) : ReadOnlyProperty<RecyclerView.ViewHolder, T> {

    private var viewBinding: T? = null

    @MainThread
    override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        checkMainThread("The viewBinding property must be used on main thread.")
        return viewBinding ?: viewBindingCreator(thisRef)
            .also { viewBinding = it }
    }
}