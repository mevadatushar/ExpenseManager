package com.example.expensemanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelper(var context: Context, var dbName: String) : SQLiteOpenHelper(context,dbName,null,2) {
    override fun onCreate(db: SQLiteDatabase?) {
                var sqlCategory ="create table categoryTB(categoryID integer primary key autoincrement,category text)"
                db?.execSQL(sqlCategory)

                var sqlAddIncome_Expense ="create table AddIncome_ExpenseTB(AddIncome_ExpenseID integer primary key autoincrement,amount integer,date text , mode text, note text ,category text,incomeExpensetype integer)"

                db?.execSQL(sqlAddIncome_Expense)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun InsertRecord(category: String)
    {
        var db = writableDatabase
        var c = ContentValues()
        c.put("category",category)
        db.insert("categoryTB",null,c)

    }

    fun InsertRecordAddIncoemExpense( amount : Int , date: String, mode: String,note: String,category: String,
                                      incomeExpensetype: Int)
    //operation type => radio button
    {
        var db = writableDatabase
        var c = ContentValues()
        c.put("amount",amount)
        c.put("date",date)
        c.put("mode",mode)
        c.put("note",note)
        c.put("category", category) // Add category field
        c.put("incomeExpensetype", incomeExpensetype)// Add operationType field
        db.insert("AddIncome_ExpenseTB",null,c)

    }
    fun DisplayRecord():ArrayList<categoryModal>
    {
        var list : ArrayList<categoryModal> = ArrayList()
        var db = readableDatabase
        var sql="select * from categoryTB"
        var cursor =db.rawQuery(sql,null)

        if(cursor.count  > 0)
        {
            cursor.moveToFirst()
            do{

                var categoryID= cursor.getInt(0)
                var category= cursor.getString(1)


                var model=categoryModal(categoryID,category)
                list.add(model)
            }while (cursor.moveToNext())
        }
        return list
    }

    fun DisplayRecordAddIncoemExpense():ArrayList<addIncomeExpenseModal>
    {
        var list : ArrayList<addIncomeExpenseModal> = ArrayList()
        var db = readableDatabase
        var sql="select * from AddIncome_ExpenseTB"
        var cursor =db.rawQuery(sql,null)

        if(cursor.count  > 0)
        {
            cursor.moveToFirst()
            do{

                var AddIncome_ExpenseID= cursor.getInt(0)
                var amount= cursor.getInt(1)
                var date= cursor.getString(2)
                var mode= cursor.getString(3)
                var note= cursor.getString(4)
                val category = cursor.getString(5) // Add category field
                var incomeExpensetype = cursor.getInt(6)  // Add operationType field

                var model=addIncomeExpenseModal(AddIncome_ExpenseID,amount,date,mode,note,category,incomeExpensetype)
                list.add(model)
            }while (cursor.moveToNext())
        }
        return list
    }

}