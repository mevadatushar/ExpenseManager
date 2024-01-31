package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.expensemanager.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabaseHelper

    lateinit var txtDate: TextView
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {

        // Set a click listener for the back button
        binding.imgBack.setOnClickListener {
            // Finish this activity and go back to the previous one
            finish()
        }


      

        with(binding)
        {

            binding.imgAddCategory.setOnClickListener {
                myDb.InsertRecord(edtCategory.text.toString())
                Toast.makeText(this@CategoryActivity, "Category added successfully", Toast.LENGTH_SHORT).show();

                finish()

            }

        }


        myDb = MyDatabaseHelper(this, dbName)

    }
}