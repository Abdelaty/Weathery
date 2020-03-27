package com.kmsoftapp.weathery.internal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction =
        beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    fragmentTransaction.func()
    fragmentTransaction.commit()
}
