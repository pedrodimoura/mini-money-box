package com.example.minimoneybox.common.presentation.binding

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> =
    FragmentBindingProperty { fragment ->
        fragment.requireView().bind(T::class.java)
    }

class FragmentBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (Fragment) -> T,
) : ReadOnlyProperty<Fragment, T> {

    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        checkMainThread("The ViewBindin property must be used on main thread.")
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return viewBinding ?: viewBindingCreator(thisRef).also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : LifecycleEventObserver {

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                source.lifecycle.removeObserver(this)
                val mainHandler = Handler(Looper.getMainLooper())
                mainHandler.post {
                    viewBinding = null
                }
            }
        }
    }
}