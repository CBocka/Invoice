package com.hamilton.customer.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CustomerBaseFragmentDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (requireArguments() != null) {
            val title: String? = requireArguments().getString(TITLE);
            val message:String? = requireArguments().getString(MESSAGE);
            val request:String? = requireArguments().getString(REQUEST);

            val builder:AlertDialog.Builder = AlertDialog.Builder(getContext());

            builder.setTitle(title);
            builder.setMessage(message);
            builder.setCancelable(false)


            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                val bundle = Bundle()
                bundle.putBoolean(BUNDLE, true)
                activity?.supportFragmentManager?.setFragmentResult(request!!, bundle)
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, which -> dismiss() };
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            return dialog;
        }

        return super.onCreateDialog(savedInstanceState)
    }
    companion object{
        val TITLE:String = "title"
        val MESSAGE:String = "message"
        val BUNDLE:String = "bundle"
        val REQUEST:String = "request"

    }
}