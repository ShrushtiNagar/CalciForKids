package com.shrushti.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.shrushti.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var addDecimal = true
    private var newOp = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        val adRequest2 = AdRequest.Builder().build()
        val adRequest3 = AdRequest.Builder().build()
        val adRequest4 = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView2.loadAd(adRequest2)
        binding.adView3.loadAd(adRequest3)
        binding.adView4.loadAd(adRequest4)

        binding.adView.adListener = object : AdListener() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                binding.adView.loadAd(adRequest)
            }
        }
        binding.adView2.adListener = object : AdListener() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                binding.adView2.loadAd(adRequest2)
            }
        }
        binding.adView3.adListener = object : AdListener() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                binding.adView3.loadAd(adRequest3)
            }
        }
        binding.adView4.adListener = object : AdListener() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                binding.adView4.loadAd(adRequest4)
            }
        }

        binding.btn5.setOnClickListener {
            addDigitToEditText("7")
        }
        binding.btn6.setOnClickListener {
            addDigitToEditText("8")
        }
        binding.btn7.setOnClickListener {
            addDigitToEditText("9")
        }
        binding.btn9.setOnClickListener {
            addDigitToEditText("4")
        }
        binding.btn10.setOnClickListener {
            addDigitToEditText("5")
        }
        binding.btn11.setOnClickListener {
            addDigitToEditText("6")
        }
        binding.btn13.setOnClickListener {
            addDigitToEditText("1")
        }
        binding.btn14.setOnClickListener {
            addDigitToEditText("2")
        }
        binding.btn15.setOnClickListener {
            addDigitToEditText("3")
        }
        binding.btn17.setOnClickListener {
            addDigitToEditText("0")
        }
        binding.btn1.setOnClickListener {
            binding.etResult.setText("0")
            binding.tvResult.text = ""
        }
        binding.btn16.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (newOp && value != "0" && value.isNotEmpty()) {
                binding.etResult.append("+")
                newOp = false
                addDecimal = true
            }
        }
        binding.btn12.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (newOp && value != "0" && value.isNotEmpty()) {
                binding.etResult.append("-")
                newOp = false
                addDecimal = true
            }
        }
        binding.btn8.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (newOp && value != "0" && value.isNotEmpty()) {
                binding.etResult.append("*")
                newOp = false
                addDecimal = true
            }
        }
        binding.btn3.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (newOp && value != "0" && value.isNotEmpty()) {
                binding.etResult.append("/")
                newOp = false
                addDecimal = true
            }
        }
        binding.btn2.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (newOp && value != "0" && value.isNotEmpty()) {
                binding.etResult.append("%")
                newOp = false
                addDecimal = true
            }
        }
        binding.btn4.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (value != "0" && value.isNotEmpty()) {
                val char = value[value.length - 1]
                if (char == '.') {
                    addDecimal = true
                } else if (char == '-' || char == '*' || char == '/' || char == '+') {
                    newOp = true
                }
                binding.etResult.setText(value.subSequence(0, value.length - 1))
            }
        }
        binding.btn18.setOnClickListener {
            if (addDecimal) {
                binding.etResult.append(".")
                addDecimal = false
            }
        }

        binding.btn19.setOnClickListener {
            val value = binding.etResult.text.toString()
            if (value != "0" && value.isNotEmpty() && newOp) {
                val res = calculations(value)
                binding.tvResult.text = res
                addDecimal = true

            } else {
                Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun calculations(value: String): String {
        var listOfDigits = divideString(value)
        if (listOfDigits.isEmpty()) {
            return ""
        }

        while (listOfDigits.contains('%')) {
            listOfDigits = moduloCalc(listOfDigits)
            if (listOfDigits.isEmpty()) {
                return ""
            }
        }
        while (listOfDigits.contains('*') || listOfDigits.contains('/')) {
            listOfDigits = mulDivide(listOfDigits)
        }

        val result = addSub(listOfDigits)
        return result.toString()
    }

    private fun divideString(str: String): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currDigit = ""
        for (i in str) {
            if (i.isDigit() || i == '.') {
                currDigit += i
            } else {
                list.add(currDigit.toFloat())
                list.add(i)
                currDigit = ""
            }
        }
        if (currDigit != "") list.add(currDigit.toFloat())
        return list
    }

    private fun moduloCalc(list: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()

        var restartIndex = list.size

        for (i in list.indices) {

            if (list[i] is Char && i < restartIndex) {
                val prev = list[i - 1] as Float
                val next = list[i + 1] as Float

                if (list[i] == '%') {
                    val res = prev % next
                    newList.add(res)
                    restartIndex = i + 1
                } else {
                    newList.add(prev)
                    newList.add(list[i])
                }
            } else if (i > restartIndex) {
                newList.add(list[i])
            }
        }
        return newList
    }

    private fun mulDivide(list: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = list.size
        for (i in list.indices) {
            if (list[i] is Char && i < restartIndex) {
                val prev = list[i - 1] as Float
                val op = list[i]
                val next = list[i + 1] as Float

                if (op == '*') {
                    val result = prev * next
                    newList.add(result)
                    restartIndex = i + 1
                } else if (op == '/') {
                    val result = prev / next
                    newList.add(result)
                    restartIndex = i + 1
                } else {
                    newList.add(prev)
                    newList.add(op)
                }
            } else if (i > restartIndex) {
                newList.add(list[i])
            }
        }
        return newList
    }

    private fun addSub(list: MutableList<Any>): Float {
        var result = list[0] as Float

        for (i in list.indices) {
            if (list[i] is Char) {
                val op = list[i]
                val next = list[i + 1] as Float
                when (op) {
                    '+' -> result += next
                    '-' -> result -= next
                }
            }
        }
        return result
    }

    private fun addDigitToEditText(digit: String) {
        var value = binding.etResult.text.toString()
        if (value != "0" && value.isNotEmpty()) {
            value += digit
        } else {
            value = digit
        }
        binding.etResult.setText(value)
        addDecimal = true
        newOp = true
    }

}