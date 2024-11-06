// LanguageUtil.kt
package com.example.expensemanager

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageUtil {
    fun applyLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)


    }




    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }
}
