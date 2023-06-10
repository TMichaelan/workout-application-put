package com.example.mobile_applications_project_put.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mobile_applications_project_put.fragments.*

class TabsPageAdapter (
    private val context: Context,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        val sharedPref = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val loggedIn = sharedPref.getBoolean("logged_in", false)

        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("homeFragment", "Home Fragment")
                val firstFragment = HomeFragment()
                firstFragment.arguments = bundle
                firstFragment
            }

            1 -> {
                val bundle = Bundle()
                bundle.putString("bodyPartsListFragment", "Body Parts Fragment")
                val secondFragment = BodyPartsListFragment()
                secondFragment.arguments = bundle
                secondFragment
            }
            2 -> if (loggedIn) {
                // If the user is logged in, then we create and return the FourthFragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Third Fragment")
                val thirdFragment = WorkoutsFragment() // This fragment should be created by you
                thirdFragment.arguments = bundle
                thirdFragment
            } else NotLoggedInFragment()

            3 -> {
                val bundle = Bundle()
                bundle.putString("bodyPartsListFragment", "Body Parts Fragment")
                val fourthFragment = TestFragment()
                fourthFragment.arguments = bundle
                fourthFragment
            }

            else -> HomeFragment()
        }
    }

//    override fun getItemCount(): Int {
//        val sharedPref = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        val loggedIn = sharedPref.getBoolean("logged_in", false)
//        return if (loggedIn) 4 else 3
//
//    }
    override fun getItemCount(): Int {
        val sharedPref = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val loggedIn = sharedPref.getBoolean("logged_in", false)
        return 4
    }

}


