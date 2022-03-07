package com.daou.scrollbottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.daou.lib.hide
import com.daou.lib.model.*
import com.daou.lib.show
import com.daou.scrollbottomnavigation.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentList = listOf<BaseFragment>(HomeFragment())
    private val sheetBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.clSlideMenuWrap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigationView()
        //가로모드에서 메뉴가 올라오는 현상 방지
        sheetBehavior.isDraggable = false
        setBottomTabItemList()
    }

    private fun initBottomNavigationView() {
        with(binding) {
            mainViewPagerAdapter = ViewPagerFragmentAdapter(this@MainActivity, fragmentList)
            bottomNav.setOnClickFixedMenu {
                invertMenuBehavior()
            }
            bottomNav.setOnNavigationItemSelectedListener { tabItem ->
                val isMenuOpened = sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED
                if (isMenuOpened) {
                    sheetBehavior.hide()
                }
                when (tabItem.type) {
                    HOME, NOTIFICATION -> {
                        val index = fragmentList.indexOfFirst { it.key == tabItem.type.typeString }
                        if (index != -1) switchTab(index, tabItem)
                    }
                    is WEB -> {}
                    else -> {}
                }
            }
        }
    }

    private fun switchTab(menuIndex: Int, tabItem: BottomTabItem) {
        binding.viewPager.setCurrentItem(menuIndex, false)
        changeSlideMenuState(willShowMenu = false)
    }

    private fun changeSlideMenuState(willShowMenu: Boolean) {
        if (willShowMenu) {
            sheetBehavior.show()
            setFixedMenuIconRes(R.drawable.ic_cancel)
        } else {
            setFixedMenuIconRes(R.drawable.ic_menu_normal)
            sheetBehavior.hide()
        }
    }

    private fun setFixedMenuIconRes(resId: Int) {
        binding.bottomNav.setFixedMenuIcon(resId)
    }

    private fun invertMenuBehavior() {
        if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            changeSlideMenuState(false)
        } else {
            changeSlideMenuState(true)
        }
    }

    private fun setBottomTabItemList() {
        val menuList = listOf(
            BottomTabItem(
                appName = "menu1",
                text = "menu1",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
            BottomTabItem(
                appName = "menu2",
                text = "menu2",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
            BottomTabItem(
                appName = "menu3",
                text = "menu3",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
            BottomTabItem(
                appName = "menu4",
                text = "menu4",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
            BottomTabItem(
                appName = "menu5",
                text = "menu5",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
            BottomTabItem(
                appName = "menu6",
                text = "menu6",
                badgeContent = null,
                type = HOME,
                iconRes = R.drawable.icon_bottom_menu_heart,
                isClicked = false,
                isDormant = false
            ),
        )
        binding.bottomNav.setBottomTabItemList(menuList)
    }
}