package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensemanager.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabaseHelper
    lateinit var adapter    : ReportsAdapter
    private lateinit var binding: ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }

    private fun initview() {
        with(binding)
        {
            binding.imgBack.setOnClickListener {
                // Finish this activity and go back to the previous one
                finish()
            }


            myDb = MyDatabaseHelper(this@ReportActivity, dbName)
            var list = myDb.DisplayRecordAddIncoemExpense()
            adapter = ReportsAdapter(list,
                deleteClick = { id ->
                    //Alert Diloge Box

                    val builder = AlertDialog.Builder(this@ReportActivity)
                    val inflater = layoutInflater
                    val dialogLayout = inflater.inflate(R.layout.custom_dilogebox_layout, null)

                    builder.setView(dialogLayout)

                    val dialog = builder.create()

                    // Initialize buttons
                    val btnCancel = dialogLayout.findViewById<Button>(R.id.btnCancel)
                    val btnDelete = dialogLayout.findViewById<Button>(R.id.btnDelete)

                    btnCancel.setOnClickListener {
                        // Handle Cancel button click
                        dialog.dismiss()
                    }

                    btnDelete.setOnClickListener {
                        // Handle Delete button click
                        // Put your delete logic here

                        myDb.deleteData(id)
                        var list = myDb.DisplayRecordAddIncoemExpense()
                        Toast.makeText(
                            this@ReportActivity,
                            "Data Deleted Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.UpdataData(list)
                        dialog.dismiss()
                    }

                    dialog.show()
                })

            var manager = LinearLayoutManager(this@ReportActivity, LinearLayoutManager.VERTICAL, false)

            recyclerview.layoutManager = manager
            recyclerview.adapter = adapter

        }
    }
}