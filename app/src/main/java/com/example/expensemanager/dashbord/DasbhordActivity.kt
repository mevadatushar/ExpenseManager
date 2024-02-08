package com.example.expensemanager.dashbord

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.AddExpenseActivity
import com.example.expensemanager.CategoryActivity
import com.example.expensemanager.MyDatabaseHelper
import com.example.expensemanager.ReportActivity
import com.example.expensemanager.databinding.ActivityDasbhordBinding
import com.example.expensemanager.dbName
import com.example.expensemanager.notifications.AlarmReceiver
import java.util.Calendar


class DasbhordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDasbhordBinding
    private lateinit var myDb: MyDatabaseHelper
    private var currentIncome: Int = 0
    private var currentExpense: Int = 0
    private var currentBalance: Int = 0
    private val REPORT_REQUEST_CODE = 123
    private val NOTIFICATION_REMINDER_NIGHT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDasbhordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDb = MyDatabaseHelper(this@DasbhordActivity, dbName)
        initview()
        ScheduleAlarm()

    }

    private fun ScheduleAlarm() {
        with(binding)
        {
            val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(this@DasbhordActivity, AlarmReceiver::class.java).let { intent ->
                intent.action = "ALARM_ACTION"
                PendingIntent.getBroadcast(this@DasbhordActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }


            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 18)
                set(Calendar.SECOND,0)
            }

            alarmMgr.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent
            )

        }


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
                startActivity(Intent(this@DasbhordActivity, CategoryActivity::class.java))
            }

            llReport.setOnClickListener {
                val i = Intent(this@DasbhordActivity, ReportActivity::class.java)
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


