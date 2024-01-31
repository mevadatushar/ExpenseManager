package com.example.expensemanager.dashbord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensemanager.AddExpenseActivity
import com.example.expensemanager.CategoryActivity
import com.example.expensemanager.databinding.ActivityDasbhordBinding

class Dasbhord_Activity : AppCompatActivity() {

    private lateinit var binding : ActivityDasbhordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDasbhordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }

    private fun initview() {

        with(binding)
        {


            llAddIncome.setOnClickListener {
                // Open the desired activity (replace MainActivity::class.java with your desired activity)
                val intentIncome = Intent(this@Dasbhord_Activity, AddExpenseActivity::class.java)
                intentIncome.putExtra("operationType", "Income") // Add this line for "ADD INCOME"
                startActivity(intentIncome)
            }

            llAddExpense.setOnClickListener {
                // Open the desired activity (replace MainActivity::class.java with your desired activity)
                val intentExpence = Intent(this@Dasbhord_Activity, AddExpenseActivity::class.java)
                intentExpence.putExtra("operationType", "Expense") // Add this line for "ADD EXPENSE"
                startActivity(intentExpence)
            }

            binding.llAddCategory.setOnClickListener {
                var i = Intent(this@Dasbhord_Activity, CategoryActivity::class.java)
                startActivity(i)
            }


        }



    }
}