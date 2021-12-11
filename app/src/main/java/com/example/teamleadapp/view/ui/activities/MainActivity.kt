package com.example.teamleadapp.view.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.teamleadapp.R
import com.example.teamleadapp.view.ui.empList.EmpListFragment
import com.example.teamleadapp.view.ui.lead.LeadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        val empListFragment = EmpListFragment.newInstance()
        val leadFragment = LeadFragment.newInstance()

        setCurrentFragment(leadFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_emp -> setCurrentFragment(empListFragment)
                R.id.action_lead -> setCurrentFragment(leadFragment)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }


    companion object {
        lateinit var fm: FragmentManager
    }
}