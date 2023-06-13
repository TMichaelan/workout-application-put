package com.example.mobile_applications_project_put.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mobile_applications_project_put.fragments.*
import com.example.mobile_applications_project_put.functions.UserUtility.isInternetAvailable

//import com.example.mobile_applications_project_put.fragments.MapsFragment

class TabsPageAdapter (
    private val context: Context,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        val sharedPref = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val loggedIn = sharedPref.getBoolean("logged_in", false)
        val internet = isInternetAvailable(context)

        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("homeFragment", "Home Fragment")
                val firstFragment = HomeFragment()
                firstFragment.arguments = bundle
                firstFragment

            }

            1 ->if (internet) {
                    if (loggedIn) {
                    // If the user is logged in, then we create and return the FourthFragment
                    val bundle = Bundle()
                    bundle.putString("fragmentName", "Third Fragment")
                    val secondFragment = WorkoutsFragment() // This fragment should be created by you
                    secondFragment.arguments = bundle
                    secondFragment
                } else NotLoggedInFragment()
            } else NoInternetFragment()

            2 ->if (internet) {
                    if (loggedIn) {
                    // If the user is logged in, then we create and return the FourthFragment
                    val bundle = Bundle()
                    bundle.putString("fragmentName", "Third Fragment")
                    val thirdFragment = ProfileFragment() // This fragment should be created by you
                    thirdFragment.arguments = bundle
                    thirdFragment
                } else NotLoggedInFragment()
            } else NoInternetFragment()

//            3 -> {
//                val bundle = Bundle()
//                bundle.putString("bodyPartsListFragment", "Body Parts Fragment")
//                val fourthFragment = MapsFragment()
//                fourthFragment.arguments = bundle
//                fourthFragment
//            }


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
        return 3
    }


}


