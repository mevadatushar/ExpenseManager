package com.example.expensemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ReportsAdapter(var list: ArrayList<addIncomeExpenseModal>, var deleteClick: ((id: Int) -> Unit)) : RecyclerView.Adapter<ReportsAdapter.MyViewHolder>() {
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v)  {
        var containerLayout: LinearLayout = v.findViewById(R.id.containerLayout)
        var imgDelete: ImageView = v.findViewById(R.id.imgDelete)
        var txtchange: TextView = v.findViewById(R.id.txtchange)
        var txtamount: TextView = v.findViewById(R.id.txtamount)
        var txtdate: TextView = v.findViewById(R.id.txtdate)
        var txtcategory: TextView = v.findViewById(R.id.txtcategory)
        var txtnote: TextView = v.findViewById(R.id.txtnote)
        var txtpaymentmethod: TextView = v.findViewById(R.id.txtpaymentmethod)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.repor_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReportsAdapter.MyViewHolder, position: Int) {
       with(holder)
       {

           txtchange.text = list.get(position).incomeExpensetype.toString()
          txtamount.text = list.get(position).amount.toString()
           txtcategory.text = list.get(position).category
           txtdate.text = list.get(position).date
           txtnote.text = list.get(position).note
           txtpaymentmethod.text = list.get(position).mode


           // Set background tint based on income or expense
           val context = itemView.context
           val backgroundTintResId = if (list[position].incomeExpensetype == 0) R.color.green else R.color.red

           containerLayout.backgroundTintList = ContextCompat.getColorStateList(context, backgroundTintResId)

           // Display "Income" or "Expense" based on the operationType
           txtchange.text = if (list[position].incomeExpensetype == 0) "Income" else "Expense"


           imgDelete.setOnClickListener {
               deleteClick.invoke(list[position].id)
           }



       }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun UpdataData(list: ArrayList<addIncomeExpenseModal>) {
        this.list = ArrayList()
        this.list = list
        notifyDataSetChanged()
    }

}