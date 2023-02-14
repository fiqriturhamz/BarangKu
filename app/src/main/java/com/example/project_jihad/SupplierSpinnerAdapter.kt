package com.example.project_jihad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.ResponseGetSupplier
import com.example.project_jihad.model.Supplier

class SupplierSpinnerAdapter(
    context: Context,
    private val resource: Int,
    private val suppliers: ArrayList<Supplier>
) : ArrayAdapter<Supplier>(context, resource, suppliers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, parent)
    }

    private fun createItemView(position: Int, parent: ViewGroup): View {
        val supplier = suppliers[position]
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = supplier.nama
        return view
    }
}

