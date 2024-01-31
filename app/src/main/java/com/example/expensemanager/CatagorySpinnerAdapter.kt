package com.example.expensemanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CatagorySpinnerAdapter(var context: Context, var list: ArrayList<categoryModal>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position] // Return the actual item at the specified position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = LayoutInflater.from(context)
        var itemView = convertView ?: inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
        var text1: TextView = itemView.findViewById(android.R.id.text1)

        text1.text = list[position].category

        return itemView
    }
}
