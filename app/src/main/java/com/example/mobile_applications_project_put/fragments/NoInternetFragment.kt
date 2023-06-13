package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.RegisterActivity
import com.example.mobile_applications_project_put.databinding.FragmentNotloggedBinding


class NoInternetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_internet, container, false)
    }

}