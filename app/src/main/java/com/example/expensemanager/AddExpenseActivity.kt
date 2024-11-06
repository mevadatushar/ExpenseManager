package com.example.expensemanager

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.expensemanager.databinding.ActivityAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabaseHelper

    lateinit var txtDate: TextView
    private lateinit var binding: ActivityAddExpenseBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Apply saved language
        LanguageUtil.applyLanguage(this, LanguageUtil.getSavedLanguage(this))
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }

    private fun initview() {

        val currentDate =
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        binding.txtDate.text = currentDate

        // Set a click listener for the back button
        binding.imgBack.setOnClickListener {
            // Finish this activity and go back to the previous one
            finish()
        }

        with(binding) {
            txtDate.setOnClickListener {
                showDatePickerDialog()
            }

            imgSubmit.setOnClickListener {
                val amountText = edtAmount.text.toString()
                val date = txtDate.text.toString()
                val mode = edtMode.text.toString()
                val note = edtNote.text.toString()

                // Check if all required details are entered
                if (amountText.isNotEmpty() && date.isNotEmpty() && mode.isNotEmpty()) {
                    val amount = amountText.toInt()

                    // Determine the selected operation type (Income or Expense)  TYPE >> incomeExpensetype
                    val incomeExpensetype = if (rbIncome.isChecked) 0 else 1

                    // Get the selected category from the spinner
                    val selectedCategory = spCategory.selectedItem

                    // Check if the selected item is of the expected type (categoryModal)
                    if (selectedCategory is categoryModal) {
                        // Insert the data into the database
                        myDb.InsertRecordAddIncoemExpense(
                            amount, date, mode, note, selectedCategory.category, incomeExpensetype
                        )
                        Toast.makeText(this@AddExpenseActivity, "Data Submitted Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                        Log.e(
                            "TAG",
                            "initview: " + incomeExpensetype + " " + amount + " " + date + " " + selectedCategory + " " + mode + " " + note
                        )
                    } else {
                        // Show a toast for unexpected category selection
                        Toast.makeText(
                            this@AddExpenseActivity,
                            "Unexpected category selection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Show a toast for incomplete details
                    Toast.makeText(this@AddExpenseActivity, "Enter all details", Toast.LENGTH_SHORT)
                        .show()
                }
            }


            val operationType = intent.getStringExtra("operationType")

            // Select the appropriate radio button based on the operationType
            if (operationType == "Expense") {
                rbExpense.isChecked = true
                binding.txtNameField.text = getString(R.string.AddExpense)
            } else if (operationType == "Income") {
                rbIncome.isChecked = true
                binding.txtNameField.text = getString(R.string.AddIncome)
            }
//object of MyDatabaseHelper
            myDb = MyDatabaseHelper(this@AddExpenseActivity, dbName)

            var list = myDb.DisplayRecord()
            val adapter = CatagorySpinnerAdapter(this@AddExpenseActivity, list)
            spCategory.adapter = adapter
        }


    }


    private fun showDatePickerDialog() {
        // Use the current date as the default date in the picker
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@AddExpenseActivity,
            { _, year, monthOfYear, dayOfMonth -> // Format the date and set it to the EditText
                val formattedDay = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val formattedMonth =
                    if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
                val selectedDate = "$formattedDay-$formattedMonth-$year"
                binding.txtDate.setText(selectedDate)
            },
            year,
            month,
            day
        )

        // Set the maximum date to the current date to prevent selecting future dates
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }

}