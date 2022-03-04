package com.daou.scrollbottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.daou.scrollbottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentList = listOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        with(binding) {
            mainViewPagerAdapter = ViewPagerFragmentAdapter(this@MainActivity, fragmentList)
            bottomNav.setOnClickFixedMenu {

            }
            bottomNav.setOnNavigationItemSelectedListener { tabItem ->
            }
        }
    }
}