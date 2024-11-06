// SettingActivity.kt
package com.example.expensemanager

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private var binding: ActivitySettingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved language
        LanguageUtil.applyLanguage(this, LanguageUtil.getSavedLanguage(this))

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding!!.btnChangeLanguage.setOnClickListener { showChangeLanguageDialog() }

        // Set a click listener for the back button
        binding!!.imgBack.setOnClickListener {
            // Finish this activity and go back to the previous one
            finish()
        }

    }

    private fun showChangeLanguageDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_change_language, null)
        builder.setView(dialogView)

        val radioGroupLanguages = dialogView.findViewById<RadioGroup>(R.id.radio_group_languages)

        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("language", "en")

        // Check the radio button based on the selected language
        when (selectedLanguage) {
            "hi" -> radioGroupLanguages.check(R.id.radio_hindi)
            "gu" -> radioGroupLanguages.check(R.id.radio_gujarati)
            "zh" -> radioGroupLanguages.check(R.id.radio_chinese)
            else -> radioGroupLanguages.check(R.id.radio_english) // Default to English
        }

        val dialog = builder.create()

        radioGroupLanguages.setOnCheckedChangeListener { _, checkedId ->
            val myEdit = sharedPreferences.edit()
            var languageCode = "en" // default to English

            when (checkedId) {
                R.id.radio_hindi -> languageCode = "hi"
                R.id.radio_gujarati -> languageCode = "gu"
                R.id.radio_chinese -> languageCode = "zh"
                R.id.radio_english -> languageCode = "en"
            }
            myEdit.putString("language", languageCode)
            myEdit.apply()

            dialog.dismiss()

            // Restart the application to apply the language change
            val intent = Intent(this@SettingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        dialog.show()
    }
}
