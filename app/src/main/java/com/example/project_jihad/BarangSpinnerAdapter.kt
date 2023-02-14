package com.example.project_jihad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.project_jihad.model.Barang
import com.example.project_jihad.model.JenisBarang

class BarangSpinnerAdapter(
    context: Context,
    private val resource: Int,
    private val barang: ArrayList<Barang>
) : ArrayAdapter<Barang>(context, resource, barang) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, parent)
    }

    private fun createItemView(position: Int, parent: ViewGroup): View {
        val barang = barang[position]
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = barang.namaBarang
        return view
    }
}