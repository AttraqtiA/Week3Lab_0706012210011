package com.example.week3lab_0706012210011.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week3lab_0706012210011.R

// Toast hanya muncul di emulator/HP, begitu pula kalau button tidak bisa diklik

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalculator() {
    var name by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }

    var isAgeValid by rememberSaveable { mutableStateOf(true) }

    var resultbutton by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFDA90F9))
                .padding(20.dp)
        ) {
            Text(
                text = "Age Calculator",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_tag_faces_24),
                contentDescription = "Ages",
                modifier = Modifier.size(100.dp)
            )

            AgeTextField(
                value = name,
                onValueChanged = { name = it },
                text = "Enter your name",
                alerttext = "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                isAgeValid = false //always false for name
            )

            AgeTextField(
                value = year,
                onValueChanged = { year = it },
                text = "Enter your birth year",
                alerttext = "Please enter a valid height greater than 0",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                isAgeValid = isAgeValid
            )

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Hi, $name! Your Age is ${2023 - year.toInt()} years",
                        Toast.LENGTH_SHORT
                    ).show()

                    resultbutton = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 16.dp),
                enabled = name.isNotBlank() && year.isNotBlank() && isYearValid(year)
            ) {
                Text(text = "CALCULATE YOUR AGE", fontSize = 20.sp)
            }

            if (resultbutton) {
                Text(
                    text = "Hi, $name! Your Age is ${2023 - year.toInt()} years",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDA90F9),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                        .border(1.dp, Color(0xFFDA90F9), shape = RoundedCornerShape(30.dp))
                        .padding(24.dp),
                )
            }
        }
    }
}


fun isYearValid(value: String): Boolean {
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
fun AgeTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    alerttext: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isAgeValid: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDA90F9), unfocusedBorderColor = Color(0xFFDA90F9)
        ),
        shape = RoundedCornerShape(30.dp),
    )

    if (!isAgeValid) {
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
fun AgePreview() {
    AgeCalculator()
}
