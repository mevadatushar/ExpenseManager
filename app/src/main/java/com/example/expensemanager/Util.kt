package com.example.expensemanager

import android.content.Intent

var dbName  = "ExpenseManagerDB"

data class categoryModal(var id : Int, var category:String)

data class addIncomeExpenseModal(var id : Int , var amount : Int , var date: String,var mode: String,var note: String,var category : String,var incomeExpensetype: Int)