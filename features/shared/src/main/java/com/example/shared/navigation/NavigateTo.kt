package com.example.shared.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigateTo(destination: Destination) {
    NavHostFragment.findNavController(this).navigate(destination.uri)
}
