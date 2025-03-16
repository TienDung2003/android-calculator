package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private lateinit var txtExpression: TextView
    private var expression: String = ""  // Lưu toàn bộ biểu thức nhập vào
    private var currentInput: String = ""
    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtExpression = findViewById(R.id.txtExpression)
        txtResult = findViewById(R.id.txtResult)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnC, R.id.btnBS, R.id.btnDot, R.id.btnPlusMinus,R.id.btnCE
        )

        val listener = View.OnClickListener { v ->
            val button = v as Button
            val buttonText = button.text.toString()
            handleInput(buttonText)
        }

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }

    private fun handleInput(input: String) {
        when (input) {
            "C" -> { // Xóa toàn bộ
                expression = ""
                currentInput = ""
                num1 = 0.0
                num2 = 0.0
                operator = ""
                txtResult.text = "0"
                txtExpression.text = ""

            }
            "CE" -> { // Xóa số vừa nhập nhưng giữ biểu thức
                expression = expression.dropLast(currentInput.length)
                currentInput = ""
                txtResult.text = if (expression.isEmpty()) "0" else expression
            }
            "BS" -> { // Xóa 1 ký tự cuối cùng
                if ( operator.isNotEmpty()) {
//                    currentInput = currentInput.dropLast(1)
                    expression = expression.dropLast(2)
                    txtExpression.text = expression
                }
                else if (currentInput.isNotEmpty() ) {
                    currentInput = currentInput.dropLast(1)
                    txtResult.text= if (currentInput.isEmpty()) "0" else currentInput
                }


            }
            "=" -> { // Tính kết quả
                if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
                    num2 = currentInput.toDoubleOrNull() ?: 0.0
                    val result = calculate(num1, num2, operator)
                    txtResult.text = result.toString()
                    expression = result.toString()
                    txtExpression.text = "$num1 $operator $num2"  // Hiển thị phép tính

                    currentInput = ""
                    operator = ""
                }
                else if (currentInput.isNotEmpty()){
                    txtResult.text = currentInput
                    txtExpression.text= currentInput
                    expression = currentInput
                }
            }
            "+", "-", "x", "/" -> { // Thêm toán tử vào biểu thức
                currentInput= txtResult.text.toString()
                if (currentInput.isNotEmpty()) {
                    num1 = currentInput.toDoubleOrNull() ?: 0.0
                    operator = input
                    expression = "$num1 $operator"  // Giữ nguyên dấu phép toán trên màn hình
                    currentInput = ""
                    txtExpression.text = expression
                }
            }
            else -> { // Nhập số hoặc dấu chấm


                    currentInput += input
                    txtResult.text = currentInput



            }

        }
    }

    private fun calculate(num1: Double, num2: Double, operator: String): Double {
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "x" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else 0.0
            else -> 0.0
        }
    }
}
