package com.example.projekt2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.projekt2.data.currencyData
import com.example.projekt2.databinding.ActivityMainBinding
import com.example.projekt2.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    var convertedToCurrency = "USD"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerLoad()
        initFab()
    }
    private fun initFab() {
        binding.currbutton.setOnClickListener {
            loadCurrencyData()
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
                    Toast.makeText(this@MainActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<currencyData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@MainActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun displayCurr(currencyData: currencyData?){
        if(currencyData!=null){
            var i_2 = ""
            var i_1 = ""
            var flax = false
            var currBuffer = ""
            //binding.fromSpinner.text = convertedToCurrency[2].toString()
            for (i in currencyData.rates.toString())
            {
                if(i_2 == "")
                {
                    i_2 = i.toString()
                }
                else if(i_1 == "" && i_2 != "")
                {
                    i_1 = i.toString()
                }
                else if(i_2.toString() == convertedToCurrency[0].toString() &&  i_1.toString() == convertedToCurrency[1].toString() && i.toString() == convertedToCurrency[2].toString())
                {
                    Log.d("Tik",i_2.toString().plus("i2"))
                    Log.d("Tik",i_1.toString().plus("i1"))
                    Log.d("Tik",i.toString())
                    binding.fromSpinner.text = i_2.toString().plus(i_1.toString()).plus(i.toString())
                    flax = true
                }
                else if(flax == true)
                {
                    Log.d("Tik",i.toString())
                    if(i.toString() == ",")
                    {
                        break
                    }
                    else if(i.toString() != "=")
                    {
                        currBuffer = currBuffer.plus(i.toString())
                    }
                }
                else
                {
                    i_2 = ""
                    i_1 = ""
                    Log.d("Tik","run")
                }

            }
            binding.currBuff.text =currBuffer.toString()
            Log.d("Tik","end")
            binding.totView.text=(currBuffer.toString().toDouble().times(binding.fromint.text.toString().toDouble()
            )).toString()
        }
    }
    private fun spinnerLoad() {

        val fromSpinner: TextView = binding.fromSpinner
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
