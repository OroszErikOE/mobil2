package com.example.projekt2

import android.content.ContentValues.TAG
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projekt2.data.currencyData
import com.example.projekt2.databinding.ActivityMainBinding
import com.example.projekt2.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var convertedToCurrency = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerLoad()
        initFab()
        val animationDrawable = binding.mainLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()

    }

    private fun initFab() {
        binding.currbutton.setOnClickListener {
            if (!binding.fromint.text.isNullOrEmpty()) {
                loadCurrencyData()
            } else {
                Toast.makeText(this@MainActivity, "Nem írtál be semmit! ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadCurrencyData() {
        NetworkManager.getCurrency()?.enqueue(object : Callback<currencyData?> {
            override fun onResponse(
                call: Call<currencyData?>,
                response: Response<currencyData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayCurr(response.body())
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<currencyData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "Network request error occured, check LOG",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun displayCurr(currencyData: currencyData?) {
        if (currencyData != null) {
            var lines = currencyData.rates.toString().substring(6).split(",")
            for (i in lines) {
                if (i.contains(convertedToCurrency)) {
                    var number = 0f
                    number = i.split("=").get(1).toDouble()
                        .times(binding.fromint.text.toString().toDouble()).toFloat()
                    binding.totView.text =
                        number.toString().plus(" ").plus(convertedToCurrency.toString())
                }
            }
        }
    }

    private fun spinnerLoad() {

        val toSpinner: Spinner = binding.toSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.currency_codes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            toSpinner.adapter = adapter
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //second
        toSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Another interface callback
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()

            }
        })
    }
}
