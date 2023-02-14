package com.example.project_jihad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_jihad.R
import com.example.project_jihad.model.DataItemBarang

class BarangAdapter(private val list: ArrayList<DataItemBarang?>) :
    RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BarangAdapter.BarangViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangAdapter.BarangViewHolder, position: Int) {
        val data = list[position]
        holder.tvIdBarang.text = data?.id
        holder.tvNamaBarang.text = data?.namaBarang
        holder.tvJenisBarang.text = data?.jenisBarang
        holder.tvQuantity.text =data?.qty

    }

    override fun getItemCount(): Int = list.size
    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaBarang = itemView.findViewById<TextView>(R.id.tv_input_nama_barang)
        val tvQuantity = itemView.findViewById<TextView>(R.id.tv_input_quantity_barang)
        val tvJenisBarang = itemView.findViewById<TextView>(R.id.tv_input_jenis_barang)
        val tvIdBarang = itemView.findViewById<TextView>(R.id.tv_input_id_barang)
    }
}