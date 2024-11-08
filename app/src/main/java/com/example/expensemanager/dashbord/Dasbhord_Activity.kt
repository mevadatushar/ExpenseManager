package com.example.expensemanager.dashbord

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensemanager.AddExpenseActivity
import com.example.expensemanager.CategoryActivity
import com.example.expensemanager.LanguageUtil
import com.example.expensemanager.MyDatabaseHelper
import com.example.expensemanager.ReportActivity
import com.example.expensemanager.databinding.ActivityDasbhordBinding
import com.example.expensemanager.dbName

class Dasbhord_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDasbhordBinding
    private lateinit var myDb: MyDatabaseHelper
    private var currentIncome: Int = 0
    private var currentExpense: Int = 0
    private var currentBalance: Int = 0
    private val REPORT_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Apply saved language
        LanguageUtil.applyLanguage(this, LanguageUtil.getSavedLanguage(this))
        binding = ActivityDasbhordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDb = MyDatabaseHelper(this@Dasbhord_Activity, dbName)
        initview()
    }
    override fun onResume() {
        super.onResume()
        // Update the UI with the latest totals
        updateTotals()
    }
    private fun initview() {

        with(binding) {
            llAddIncome.setOnClickListener {
                // Open the desired activity
                openAddExpenseActivity("Income")
            }

            llAddExpense.setOnClickListener {
                // Open the desired activity
                openAddExpenseActivity("Expense")
            }

            llAddCategory.setOnClickListener {
                startActivity(Intent(this@Dasbhord_Activity, CategoryActivity::class.java))
            }

            llReport.setOnClickListener {
                val i = Intent(this@Dasbhord_Activity, ReportActivity::class.java)
                startActivityForResult(i, REPORT_REQUEST_CODE)
            }
        }
    }

    private fun openAddExpenseActivity(operationType: String) {
        val intentExpense = Intent(this, AddExpenseActivity::class.java)
        intentExpense.putExtra("operationType", operationType)
        startActivityForResult(intentExpense, ADD_EXPENSE_REQUEST_CODE)
    }

    private fun updateTotals() {
        // Retrieve total income and total expense from the database
        currentIncome = myDb.calculateTotalIncome()
        currentExpense = myDb.calculateTotalExpense()

        // Calculate and display balance
        currentBalance = currentIncome - currentExpense
        updateUI()
    }
    private fun updateUI() {
        // Use the Indian Rupee symbol (₹) before the amounts
        val rupeeSymbol = "₹ "

        // Update your UI with the current values
        binding.txtIncomeDisplay.text = rupeeSymbol + currentIncome.toString()
        binding.txtExpenseDisplay.text = rupeeSymbol + currentExpense.toString()
        binding.txtBalanceDisplay.text = rupeeSymbol + currentBalance.toString()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_EXPENSE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Update the totals when returning from AddExpenseActivity
            updateTotals()
        }
    }

    companion object {
        private const val ADD_EXPENSE_REQUEST_CODE = 1
    }



}
