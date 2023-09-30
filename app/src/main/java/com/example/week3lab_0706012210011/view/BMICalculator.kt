package com.example.week3lab_0706012210011.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week3lab_0706012210011.R
import kotlin.math.pow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMICalculator() {
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    var dialogshow by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState)
    }, content = {
        if (dialogshow) {
            val BMIScore = weight.toDoubleOrNull()?.div((height.toDoubleOrNull()?.div(100) ?: 0.0).pow(2)) ?: 0.0
            AlertDialog(onDismissRequest = {
                dialogshow = false
            }, title = {
                Text(
                    text = "Your BMI Analysis",
                )

            }, text = {

                val bmiCategory = when {
                    BMIScore <= 18.4 -> "You are Underweight!"
                    BMIScore <= 24.9 -> "You are Normal Weight!"
                    BMIScore <= 29.9 -> "You are Overweight!"
                    else -> "You are Obese!"
                }

                Text(
                    text = "\nYour Height: ${(height.toDoubleOrNull()?.div(100) ?: 0.0)} m" +
                            "\nYour Weight: ${(weight.toIntOrNull() ?: 0)} kg" +
                            "\nYour BMI Score: ${"%.1f".format(BMIScore)}" +
                            "\n$bmiCategory"
                )


            }, confirmButton = {
                Button(onClick = {
                    dialogshow = false
                    // Handle the button click action here
                }) {
                    Text("OK")
                }
            })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_face_retouching_natural_24),
                contentDescription = "Kinclong",
                modifier = Modifier.size(100.dp)
            )

            BMITextField(
                value = weight,
                onValueChanged = { weight = it },
                text = "Weight in kg",
                alerttext = "Please enter a valid weight greater than 0",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                isBMIValid = isWeightHeightValid(weight)
            )

            BMITextField(
                value = height,
                onValueChanged = { height = it },
                text = "Height in cm",
                alerttext = "Please enter a valid height greater than 0",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                isBMIValid = isWeightHeightValid(height)
            )

            Button(
                onClick = {
                    dialogshow = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                enabled = weight.isNotBlank() && height.isNotBlank() && isWeightHeightValid(
                    weight
                ) && isWeightHeightValid(height)
            ) {
                Text(text = "Submit")
            }
        }
    })
}

fun isWeightHeightValid(value: String): Boolean {
    if (value.isNotEmpty()) {
        val intValue = value.toIntOrNull()
        if (intValue != null && intValue > 0) {
            return true
        }
    }
    return false
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMITextField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    alerttext: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isBMIValid: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue, unfocusedBorderColor = Color.Blue
        ),
        shape = RoundedCornerShape(30.dp),
    )

    if (!isBMIValid) {
        Text(
            text = alerttext,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 16.dp, bottom = 4.dp),
            color = Color.Red
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BMIPreview() {
    BMICalculator()
}