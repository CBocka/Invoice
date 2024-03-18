package com.hamilton.item.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamilton.entity.items.Item
import com.hamilton.invoice.R
import com.hamilton.item.databinding.LayoutItemListRecyclerviewBinding

class ItemListAdapter (private val onClickListener: (Item) -> Unit, private val onLongClickListener :(Item) -> Boolean) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(LayoutItemListRecyclerviewBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    fun sortByName() {
        submitList(currentList.sortedBy { it.name })
    }

    companion object{
        private val ITEM_COMPARATOR = object: DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return  oldItem.name == newItem.name
            }
        }
    }

    inner class ItemViewHolder(private val binding: LayoutItemListRecyclerviewBinding) : RecyclerView.ViewHolder (binding.root) {

        fun bind(item: Item) {
            binding.tvItemName.text = item.name
            binding.tvItemPrice.text = "Precio: ${String.format("%.2f €", item.price)}"
            binding.tvItemType.text = item.type.toString()

            when(item.photo) {
                "ferrarisf90" -> binding.imgItemPhoto.setImageResource(R.drawable.ferrarisf90)
                "am_dbx707" -> binding.imgItemPhoto.setImageResource(R.drawable.am_dbx707)
                "aventador" -> binding.imgItemPhoto.setImageResource(R.drawable.aventador)
                "corvette" -> binding.imgItemPhoto.setImageResource(R.drawable.corvette)
                "fordfocus" -> binding.imgItemPhoto.setImageResource(R.drawable.fordfocus)
                "astonmartinwec" -> binding.imgItemPhoto.setImageResource(R.drawable.astonmartinwec)
                "benz" -> binding.imgItemPhoto.setImageResource(R.drawable.benz)
                "camaro" -> binding.imgItemPhoto.setImageResource(R.drawable.camaro)
                "tesla" -> binding.imgItemPhoto.setImageResource(R.drawable.tesla)
                "" -> binding.imgItemPhoto.setImageResource(R.drawable.no_item_img)
            }

            itemView.setOnClickListener{onClickListener(item)}
            itemView.setOnLongClickListener{onLongClickListener(item)}
        }
    }
}