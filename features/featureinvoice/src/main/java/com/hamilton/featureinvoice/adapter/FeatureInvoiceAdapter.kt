package com.hamilton.featureinvoice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.featureinvoice.R
import com.hamilton.featureinvoice.databinding.ItemFeatureinvoiceBinding

class FeatureInvoiceAdapter(
    private val onClickListener: (FeatureInvoice) -> Unit,
    private val onLongClickListener: (FeatureInvoice) -> Boolean
) : ListAdapter<FeatureInvoice, FeatureInvoiceAdapter.InvoiceViewHolder>(INVOICE_COMPARATOR){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(
            layoutInflater.inflate(
                R.layout.item_featureinvoice,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = currentList[position]
        holder.render(item, onClickListener, onLongClickListener)
    }
fun sort() {
    submitList(currentList.sortedBy { it.username })
}
  companion object{
      private val INVOICE_COMPARATOR=object :DiffUtil.ItemCallback<FeatureInvoice>(){
          override fun areItemsTheSame(oldInvoice: FeatureInvoice, newInvoice: FeatureInvoice): Boolean {
              return oldInvoice===newInvoice
          }

          override fun areContentsTheSame(oldInvoice: FeatureInvoice, newInvoice: FeatureInvoice): Boolean {
             return oldInvoice.username==newInvoice.username
          }
      }

  }
    class InvoiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFeatureinvoiceBinding.bind(view)

        fun render(
            invoice: FeatureInvoice,
            onClickListener: (FeatureInvoice) -> Unit,
            onLongClickListener: (FeatureInvoice) -> Boolean
        ) {
            with(binding) {
                tvNombreFactura.text = invoice.billname
                tvNombreCliente.text = invoice.username
                tvFechaEmision.text = invoice.dateOfIssue
                tvFechaVencimiento.text = invoice.dueDate

            }
            itemView.setOnClickListener { onClickListener(invoice) }
            itemView.setOnLongClickListener { onLongClickListener(invoice) }
        }
    }
}

