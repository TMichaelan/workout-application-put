package com.example.mobile_applications_project_put.adapters

import MapsFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mobile_applications_project_put.fragments.BodyPartsListFragment
import com.example.mobile_applications_project_put.fragments.HomeFragment
//import com.example.mobile_applications_project_put.fragments.MapsFragment

//import com.example.mobile_applications_project_put.fragments.MapsFragment

class TabsPageAdapter (
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private var numberOfTabs: Int,
    ) : FragmentStateAdapter(fm, lifecycle) {

        override fun createFragment(position: Int): Fragment {
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
//                2 -> {
//                    val bundle = Bundle()
//                    bundle.putString("fragmentName", "Third Fragment")
//                    val thirdFragment = ThirdFragment()
//                    thirdFragment.arguments = bundle
//                    thirdFragment
//                }
                3 -> {
                    val bundle = Bundle()
                    bundle.putString("fragmentName", "Forth Fragment")
                    val forthFragment = MapsFragment()
                    forthFragment.arguments = bundle
                    forthFragment
                }
                else -> HomeFragment()
            }
        }

        override fun getItemCount(): Int {
            return numberOfTabs
        }
}