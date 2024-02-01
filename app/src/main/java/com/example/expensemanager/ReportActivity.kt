package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            adapter = ReportsAdapter(list)

            var manager = LinearLayoutManager(this@ReportActivity, LinearLayoutManager.VERTICAL, false)

            binding.recyclerview.layoutManager = manager
            binding.recyclerview.adapter = adapter

        }
    }
}