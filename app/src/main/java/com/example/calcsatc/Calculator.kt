package com.example.calcsatc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun calculate(number1: Int, number2: Int, operator: String): Any {
    return when (operator) {
        "/" -> if (number2 == 0) "Error: division by 0." else number1 / number2
        "+" -> number1 + number2
        "-" -> number1 - number2
        "*" -> number1 * number2
        else -> "Invalid operator"
    }
}

@Composable
fun Calculator() {
    var calculatorValue by remember { mutableStateOf("0") }
    var lastOperator by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        val buttons = listOf(
            listOf("1", "2", "3", "+"),
            listOf("4", "5", "6", "-"),
            listOf("7", "8", "9", "*"),
            listOf("0", "C", "=", "/")
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = calculatorValue,
                modifier = Modifier.padding(16.dp)
            )
        }

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { label ->
                    Button(onClick = {
                        when {
                            label == "=" -> {
                                val expression = calculatorValue.split(" ")

                                if (expression.size == 3) {
                                    val number1 = expression[0].toIntOrNull()
                                    val number2 = expression[2].toIntOrNull()

                                    if (number1 != null && number2 != null) {
                                        calculatorValue = calculate(number1, number2, expression[1]).toString()
                                    } else {
                                        calculatorValue = "Invalid input"
                                    }
                                } else {
                                    calculatorValue = "Invalid expression"
                                }
                            }
                            label == "C" -> {
                                calculatorValue = "0"
                                lastOperator = null
                            }
                            label in listOf("+", "-", "*", "/") -> {
                                if (lastOperator == null && calculatorValue != "0") {
                                    calculatorValue += " $label "
                                    lastOperator = label
                                }
                            }
                            else -> {
                                if (calculatorValue == "0" || calculatorValue.startsWith("Invalid") || calculatorValue.startsWith("Error")) {
                                    calculatorValue = label
                                } else {
                                    calculatorValue += label
                                }
                            }
                        }
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculator() {
    Calculator()
}
