package com.example.teamleadapp.view.ui.lead

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teamleadapp.R
import kotlinx.android.synthetic.main.fragment_lead.*

class LeadFragment : Fragment(R.layout.fragment_lead) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsGPur5RpLtSh1LItd__elEiW1dNo1t9OfXw&usqp=CAU")
            .apply(RequestOptions().error(R.drawable.default_emp_img))
            .into(profileImageView)

        nameTextView.text = "Mark Kipling"
        positionTextView.text = "Team Lead"
    }

    companion object {
        fun newInstance() = LeadFragment()
    }
}