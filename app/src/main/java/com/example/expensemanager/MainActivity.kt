package com.example.expensemanager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.dashbord.DasbhordActivity
import com.example.expensemanager.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

   lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Apply saved language
        LanguageUtil.applyLanguage(this, LanguageUtil.getSavedLanguage(this))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {






        Handler().postDelayed({
            val intent = Intent(this@MainActivity, DasbhordActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }


    fun updateLocale(  languageCode : String ) {
        var locale = Locale(languageCode)
        Locale.setDefault(locale)
        //Locale.setDefault(new Locale(languageCode));
        getResources().getConfiguration().setLocale(locale)

        var resources = getResources()
        var config = resources.getConfiguration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }


}
