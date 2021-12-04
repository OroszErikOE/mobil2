package com.example.projekt2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.example.projekt2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    var baseCurrency = "EUR"
    var convertedToCurrency = "EUR"
    var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.currbutton.setOnClickListener{
            val inputText = binding.fromint?.text.toString()

            binding.totView.setText(inputText)
        }
    }
    /*private fun getApiResult() {
        if (binding.fromView != null && binding.fromint.text.isNotEmpty() && binding.fromint.text!!.isNotBlank()) {
            var access_key = "35901288dbfd728469b19baaaeda6848"
            var API =
                "http://api.exchangeratesapi.io/v1/latest?base=$baseCurrency&symbols=$convertedToCurrency"

            if (baseCurrency == convertedToCurrency) {
                binding.totView.setText(binding.fromView.toString())
            }

            /*Toast.makeText(
                    applicationContext,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()*/
        }
    }*/
}