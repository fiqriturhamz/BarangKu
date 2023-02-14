package com.example.project_jihad

import android.R.attr
import android.content.Intent
import android.widget.Toast
import com.example.project_jihad.model.DataItemGetSupplier
import com.example.project_jihad.model.Supplier
import android.R.attr.data




class SupplierData {
    companion object {
        private var instance: SupplierData? = null

        fun getInstance(): SupplierData {
            if (instance == null) {
                instance = SupplierData()
            }
            return instance!!
        }
    }

    var listSupplier = ArrayList<Supplier>()

    fun setData(list: ArrayList<Supplier>) {
        this.listSupplier = list
    }

    fun clearData() {
        listSupplier.clear()
    }
}