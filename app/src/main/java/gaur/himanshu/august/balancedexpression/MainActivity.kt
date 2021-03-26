package gaur.himanshu.august.balancedexpression

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.expression_edit_text)
        val button = findViewById<Button>(R.id.check_expression)
        val filterTextView = findViewById<TextView>(R.id.filter_stage)
        val result = findViewById<TextView>(R.id.result_text_view)
        val interMediate = findViewById<TextView>(R.id.intermediate_text_view)


        button.setOnClickListener {

            val expression = editText.text.toString().trim()
            if (expression.isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter the Expression", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val filter = filter(expression)
            filterTextView.text = "After removing all the unrequired elements : " + filter
            val value = checkExpression(filter)
            if (value) {
                result.text = "Result : Balanced Expression"
                var i = 0
                filter.toCharArray().forEach {
                    if (it == '{' || it == '(' || it == '[') {
                        i++
                        interMediate.append("${i}   ${it.toString()}")
                    }
                    if (it == '}' || it == ')' || it == ']') {
                        interMediate.append("  matches with =>   ${it} \n")
                    }
                }

            } else {
                result.text = "Result : UnBalanced Expression"
            }

        }


    }


    private fun filter(str: String): String {
        val string = StringBuilder()
        val charArray = str.toCharArray()
        charArray.forEach {
            if (it == '[' || it == ']' || it == '{' || it == '}' || it == '(' || it == ')') {
                string.append(it)
            }
        }
        Log.d("TAG", "filter: ${string}")
        return string.toString()
    }


    private fun checkExpression(list: String): Boolean {
        val s: Stack<Char> = Stack()
        val charArray = list.toCharArray()
        charArray.forEach {
            if (it == '{' || it == '(' || it == '[') {
                s.push(it)
            } else {
                if (s.isNotEmpty()) {
                    val topOfStack = s.pop()
                    val reflection = getReflection(topOfStack)
                    if (reflection != it) {
                        return false
                    }
                } else {
                    return false
                }
            }
        }
        return s.isEmpty()

    }

    private fun getReflection(char: Char): Char {
        return when (char) {
            '(' -> {
                return ')'
            }
            '{' -> {
                return '}'
            }
            '[' -> {
                return ']'
            }
            else -> {
                'i'
            }
        }
    }

}