package com.example.pengubahmatauang

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.pengubahmatauang.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding
    : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nilaiMataUang.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)}

        binding.textRupiah.isVisible = false
        binding.convertButton.setOnClickListener{konversi()}
    }

    private fun konversi() {
        val stringInTextField = binding.nilaiMataUang.text.toString()
        val nilaiUang = stringInTextField.toDoubleOrNull()
        if (nilaiUang == null) {
            binding.textRupiah.text =" "
            return
        }

        val convert = when (binding.currencyGroup.checkedRadioButtonId){
            R.id.euro_button -> 15492.28
            R.id.usd_button -> 14378.45
            R.id.yen_button -> 114.21
            else -> 4553.17
        }

        val totalRupiah = nilaiUang * convert
        val indonesianLocale = Locale("in", "ID")
        val rupiahFormat = NumberFormat.getCurrencyInstance(indonesianLocale).format(totalRupiah)
        binding.textRupiah.text = getString(R.string.rupiah_value, rupiahFormat)
        binding.textRupiah.isVisible = true

    }

    private fun handleKeyEvent(view: View, keyCode:Int): Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}