package com.example.project_jihad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_jihad.R
import com.example.project_jihad.model.DataItemGetSupplier

class SupplierAdapter (private val list : ArrayList<DataItemGetSupplier?>) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_supplier,parent,false)
        return SupplierViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val data = list[position]
        holder.idSupplier.text = data?.id
        holder.namaSupplier.text = data?.namaSupplier
        holder.namaCompanySupplier.text = data?.brandCompany
    }

    override fun getItemCount(): Int = list.size
    inner class  SupplierViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val idSupplier = itemView.findViewById<TextView>(R.id.tv_input_id_supplier)
        val namaSupplier = itemView.findViewById<TextView>(R.id.tv_input_nama_supplier)
        val namaCompanySupplier = itemView.findViewById<TextView>(R.id.tv_input_perusahaan_supplier)
    }
}