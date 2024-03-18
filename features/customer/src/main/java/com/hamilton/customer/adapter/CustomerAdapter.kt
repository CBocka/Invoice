package com.hamilton.customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamilton.customer.R
import com.hamilton.customer.databinding.ItemCustomerBinding
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.items.Item
import com.hamilton.entity.users.User

class CustomerAdapter(private val onClickListener:(Customer) -> Unit,
                      private val onLongClickListener:(Customer) -> Boolean) :
    ListAdapter<Customer, CustomerAdapter.CustomerViewHolder>(CUSTOMER_COMPARATOR){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomerViewHolder(layoutInflater.inflate(R.layout.item_customer,parent,false))
    }
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = currentList[position]
        holder.render(item,onClickListener,onLongClickListener)
    }


    fun sort() {
        submitList(currentList.sortedBy { it.name })
    }

    companion object{
        private val CUSTOMER_COMPARATOR = object: DiffUtil.ItemCallback<Customer>(){
            override fun areItemsTheSame(oldCustomer: Customer, newCustomer: Customer): Boolean {
                return oldCustomer === newCustomer
            }

            override fun areContentsTheSame(oldCustomer: Customer, newCustomer: Customer): Boolean {
                return  oldCustomer.name == newCustomer.name
            }
        }
    }


    class CustomerViewHolder(view : View): RecyclerView.ViewHolder(view){
        val binding = ItemCustomerBinding.bind(view)

        fun render(customer: Customer, onClickListener:(Customer) -> Unit, onLongClickListener: (Customer) -> Boolean){
            with(binding){
                tvID.text = customer.id
                tvName.text = customer.name
                tvEmail.text = customer.email

            }
            itemView.setOnClickListener{onClickListener(customer)}
            itemView.setOnLongClickListener{onLongClickListener(customer)}
        }
    }
}


