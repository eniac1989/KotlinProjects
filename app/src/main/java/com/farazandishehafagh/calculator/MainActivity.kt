package com.farazandishehafagh.calculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.farazandishehafagh.calculator.databinding.ActivityMainBinding
import java.lang.Math.ceil
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.costOfServiceEditText.setOnKeyListener{view,keyCode,_->handleKeyEvent(view,keyCode)}
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }
        val tipPercentage = when (binding.tipsOfQuestions.checkedRadioButtonId) {
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.20
        }
        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = ceil(tip)
        }
        displayTip(tip)

    }

    private fun displayTip(tip: Any) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}