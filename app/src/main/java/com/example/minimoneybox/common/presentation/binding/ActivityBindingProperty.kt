package com.example.minimoneybox.common.presentation.binding

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (ComponentActivity) -> T,

    ) : ReadOnlyProperty<ComponentActivity, T> {
    private var viewBinding: T? = null

    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): T {
        checkMainThread("ViewBinding property must be used on main thread")
        return viewBinding ?: viewBindingCreator(thisRef).also { viewBinding = it }
    }
}

inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int
): ReadOnlyProperty<ComponentActivity, T> = ActivityBindingProperty { activity ->
    ActivityCompat.requireViewById<View>(activity, viewBindingRootId).bind(T::class.java)
}

private const val BIND_METHOD_NAME = "bind"

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> View.bind(viewBindingClass: Class<T>): T {
    val bindMethod: Method = viewBindingClass.getMethod(BIND_METHOD_NAME, View::class.java)
    return bindMethod.invoke(null, this) as T
}