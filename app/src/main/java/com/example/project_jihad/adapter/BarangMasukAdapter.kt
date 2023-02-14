package com.example.project_jihad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_jihad.R
import com.example.project_jihad.model.DataItemBarangMasuk

class BarangMasukAdapter (private val list : ArrayList<DataItemBarangMasuk?>): RecyclerView.Adapter<BarangMasukAdapter.BarangMasukViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BarangMasukAdapter.BarangMasukViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_barang_masuk,parent,false)
        return BarangMasukViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangMasukAdapter.BarangMasukViewHolder, position: Int) {
        val getData = list[position]
     holder.tvNamaBarangMasuk.text =getData?.namaBarang
        holder.tvDeskripsiBarangMasuk.text = getData?.deskripsi
        holder.tvJenisBarang.text = getData?.jenisBarang
        holder.tvNamaSupplierBarangMasuk.text = getData?.supplier
        holder.tvQuantityBarangMasuk.text = getData?.jumlahBarang
        holder.tvTanggalBarangMasuk.text = getData?.tgl
        holder.tvIdBarangMasuk.text = getData?.id

    }

    override fun getItemCount(): Int = list.size
    inner class BarangMasukViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvNamaBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_nama_barang_di_barang_masuk)
        val tvJenisBarang = itemView.findViewById<TextView>(R.id.tv_input_jenis_barang_di_barang_masuk)
        val tvQuantityBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_quantity_barang_di_barang_masuk)
        val tvNamaSupplierBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_nama_supplier_di_barang_masuk)
        val tvTanggalBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_tanggal_barang_masuk_di_barang_masuk)
        val tvDeskripsiBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_deskripsi_barang_di_barang_masuk)
        val tvIdBarangMasuk = itemView.findViewById<TextView>(R.id.tv_input_id_barang_masuk)
    }
}