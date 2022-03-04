package com.daou.lib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daou.lib.databinding.ItemBottomTabBinding
import com.daou.lib.model.BottomTabItem
import com.daou.lib.model.TabViewDesignModel

class BottomTabListAdapter(
    private var itemWidth: Int,
    private val designModel: TabViewDesignModel
) : ListAdapter<BottomTabItem, BottomTabListAdapter.ItemViewHolder>(
    DiffCallback()
) {

    private var _onClickTab: ((BottomTabItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        ItemBottomTabBinding.inflate(LayoutInflater.from(parent.context), null, false)
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setItemWidth(width: Int) {
        itemWidth = width
    }

    fun setOnClickTab(onClick: (BottomTabItem) -> Unit) {
        _onClickTab = onClick
    }

    inner class ItemViewHolder(private val binding: ItemBottomTabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_item: BottomTabItem) {
            with(binding) {
                item = _item
                onClickTab = View.OnClickListener {
                    _onClickTab?.invoke(_item)
                }
                itemView.updateLayoutParams {
                    width = itemWidth
                }
                tabViewDesignModel = designModel
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<BottomTabItem>() {
    override fun areItemsTheSame(oldItem: BottomTabItem, newItem: BottomTabItem) =
        oldItem.type == newItem.type

    override fun areContentsTheSame(oldItem: BottomTabItem, newItem: BottomTabItem) = false
}