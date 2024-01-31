package com.shrushti.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.shrushti.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var newOp = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn5.setOnClickListener { numberEvent(binding.btn5) }
        binding.btn6.setOnClickListener { numberEvent(binding.btn6) }
        binding.btn7.setOnClickListener { numberEvent(binding.btn7) }
        binding.btn9.setOnClickListener { numberEvent(binding.btn9) }
        binding.btn10.setOnClickListener { numberEvent(binding.btn10) }
        binding.btn11.setOnClickListener { numberEvent(binding.btn11) }
        binding.btn13.setOnClickListener { numberEvent(binding.btn13) }
        binding.btn14.setOnClickListener { numberEvent(binding.btn14) }
        binding.btn15.setOnClickListener { numberEvent(binding.btn15) }
        binding.btn17.setOnClickListener { numberEvent(binding.btn17) }
        binding.btn18.setOnClickListener { numberEvent(binding.btn18) }
        binding.btn2.setOnClickListener { numberEvent(binding.btn2) }

        binding.btn4.setOnClickListener { opEvent(binding.btn4) }
        binding.btn8.setOnClickListener { opEvent(binding.btn8) }
        binding.btn12.setOnClickListener { opEvent(binding.btn12) }
        binding.btn16.setOnClickListener { opEvent(binding.btn16) }

        binding.btn1.setOnClickListener {
            binding.etResult.setText("0")
            oldNumber=""
            newOp = true
        }

        binding.btn3.setOnClickListener {
            val num = binding.etResult.text.toString().toDouble() / 100
            binding.etResult.setText(num.toString())
            newOp = true
        }

        binding.btn19.setOnClickListener {
            var newNumber = binding.etResult.text.toString()
            var finalNum: Double? = null
            try {
                when (op) {
                    "*" -> {
                        finalNum = oldNumber.toDouble() * newNumber.toDouble()
                    }

                    "/" -> {
                        finalNum = oldNumber.toDouble() / newNumber.toDouble()
                    }

                    "+" -> {
                        finalNum = oldNumber.toDouble() + newNumber.toDouble()
                    }

                    "-" -> {
                        finalNum = oldNumber.toDouble() - newNumber.toDouble()
                    }
                }
                binding.etResult.setText(finalNum.toString())
            } catch (e: Exception) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                binding.etResult.setText("0")
            }

            newOp = true
            op = ""
        }

    }

    private var minus = true
    private var dot = true
    private fun numberEvent(btn: Button?) {

        if (newOp) {
            binding.etResult.text.clear()
            newOp = false
            minus = true
            dot = true
        }
        var text = binding.etResult.text.toString()

        if(btn == binding.btn18){
            if(dot){
                text+= btn!!.text
                dot = false
            }

        }
        else if (btn!! == binding.btn2) {
            if(minus){
                text = "-$text"
                minus = false
            }

        } else text += btn!!.text

        binding.etResult.setText(text)

    }

    private var oldNumber = ""
    private var op = ""

    private fun opEvent(btn: Button?) {
        newOp = true
        op = btn!!.text.toString()
        oldNumber = binding.etResult.text.toString()

    }

}