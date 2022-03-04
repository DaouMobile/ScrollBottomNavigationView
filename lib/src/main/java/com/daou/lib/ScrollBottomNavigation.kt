package com.daou.lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daou.lib.databinding.ComponentDaouBottomNavBinding
import com.daou.lib.model.BottomTabItem
import com.daou.lib.model.ETC
import com.daou.lib.model.TabViewDesignModel

class ScrollBottomNavigation @JvmOverloads constructor(
    private val _context: Context,
    private val _attributeSet: AttributeSet? = null,
    _defStyle: Int = 0
) : ConstraintLayout(
    _context,
    _attributeSet,
    _defStyle
) {

    private val binding =
        ComponentDaouBottomNavBinding.inflate(LayoutInflater.from(context), this, true)

    private val bottomTabListAdapter: BottomTabListAdapter

    private var itemList: List<BottomTabItem>? = null

    private var _maxVisibleItemCount = 6

    private var _fixedMenuIcon = R.drawable.ic_menu_normal

    private var _fixedMenuIconActive = R.drawable.ic_cancel

    private var _fixedMenuTitle = ""

    private var tabTitleColor = R.color.black

    private var tabTitleTextColorActive = R.color.nav_tab_text_color_active

    private var _fixedMenuItem: BottomTabItem

    private val endMarginWhenTabCountOverMaxLimit = 8.dp

    private val _tabViewDesignModel: TabViewDesignModel
        get() = TabViewDesignModel(
            defaultTitleColor = ContextCompat.getColor(context, tabTitleColor),
            clickedTitleColor = ContextCompat.getColor(context, tabTitleTextColorActive)
        )

    private val itemWidth: Int
        get() = context.deviceWidth / _maxVisibleItemCount

    init {
        initAttributes()
        bottomTabListAdapter = BottomTabListAdapter(
            itemWidth,
            _tabViewDesignModel
        )
        _fixedMenuItem = BottomTabItem(
            FIXED_MENU_ID,
            _fixedMenuTitle,
            null,
            ETC,
            _fixedMenuIcon,
            isClicked = false
        )
        with(binding) {
            recyclerView.adapter = bottomTabListAdapter
            recyclerView.itemAnimator = null
            recyclerView.setHasFixedSize(false)
            bottomTabListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    super.onItemRangeChanged(positionStart, itemCount)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    updateArrowIconVisibility(layoutManager)
                }
            })
            fixedMenu.root.updateLayoutParams {
                width = itemWidth
            }
            fixedMenuItem = _fixedMenuItem
            tabViewDesignModel = _tabViewDesignModel
            executePendingBindings()
        }
        subscribeRecyclerViewScrollEvent(binding)
    }

    fun setMaxVisibleItemCount(count: Int) {
        _maxVisibleItemCount = count
    }

    fun setOnClickFixedMenu(func: (BottomTabItem) -> Unit) {
        binding.fixedMenu.root.setOnClickListener {
            func(_fixedMenuItem)
        }
    }

    fun setBottomTabItemList(_itemList: List<BottomTabItem>?) {
        itemList = _itemList?.also {
            val tabItemWidth = if (it.size < _maxVisibleItemCount) {
                binding.recyclerView.updateLayoutParams<LayoutParams> {
                    marginEnd = 8.dp
                    endToStart = LayoutParams.UNSET
                    endToEnd = LayoutParams.PARENT_ID
                }
                (context.deviceWidth - 8.dp) / (it.size + 1)
            } else {
                binding.recyclerView.updateLayoutParams<LayoutParams> {
                    marginEnd = 0.dp
                    endToEnd = LayoutParams.UNSET
                    endToStart = binding.rightButton.id
                }
                (context.deviceWidth - endMarginWhenTabCountOverMaxLimit) / _maxVisibleItemCount
            }
            bottomTabListAdapter.setItemWidth(tabItemWidth)
            updateFixedMenuWidth(tabItemWidth)
            bottomTabListAdapter.submitList(it)
        }
    }

    fun setFixedMenuIcon(@DrawableRes iconRes: Int?) {
        binding.fixedMenu.iconView.setIconDrawable(iconRes ?: _fixedMenuIcon)
    }

    fun setOnNavigationItemSelectedListener(onClick: (BottomTabItem) -> Unit) {
        bottomTabListAdapter.setOnClickTab(onClick)
    }

    private fun updateArrowIconVisibility(layoutManager: LinearLayoutManager) {
        if (layoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
            if (itemList != null && itemList?.size != 0) binding.leftButton.show()
        } else {
            binding.leftButton.invisible()
        }

        if (layoutManager.findLastCompletelyVisibleItemPosition() != itemList?.lastIndex) {
            binding.rightButton.show()
        } else {
            binding.rightButton.gone()
        }
    }

    private fun subscribeRecyclerViewScrollEvent(binding: ComponentDaouBottomNavBinding) {
        with(binding.recyclerView) {
            clearOnScrollListeners()
            val tabLayoutManger = layoutManager as LinearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    updateArrowIconVisibility(tabLayoutManger)
                }
            })
        }
    }

    private fun updateFixedMenuWidth(_width: Int) {
        binding.fixedMenu.root.updateLayoutParams {
            width = _width
        }
    }

    private fun initAttributes() {
        _attributeSet?.let {
            _context.obtainStyledAttributes(_attributeSet, R.styleable.ScrollBottomNavigation).run {
                tabTitleColor = getResourceId(
                    R.styleable.ScrollBottomNavigation_title_text_color,
                    tabTitleColor
                )
                tabTitleTextColorActive =
                    getResourceId(
                        R.styleable.ScrollBottomNavigation_title_text_color_active,
                        tabTitleTextColorActive
                    )
                _fixedMenuIcon = getResourceId(
                    R.styleable.ScrollBottomNavigation_fixed_menu_icon,
                    _fixedMenuIcon
                )
                _fixedMenuIconActive =
                    getResourceId(
                        R.styleable.ScrollBottomNavigation_fixed_menu_icon_active,
                        _fixedMenuIconActive
                    )
                _fixedMenuTitle = getString(R.styleable.ScrollBottomNavigation_fixed_menu_title)
                    ?: _fixedMenuTitle
            }
        }
    }

    companion object {
        const val FIXED_MENU_ID = "fixed_menu"
    }
}
