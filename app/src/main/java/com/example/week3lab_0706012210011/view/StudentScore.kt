
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
fun StudentScore() {
    var Shawn by rememberSaveable { mutableStateOf("") }
    var Pete by rememberSaveable { mutableStateOf("") }
    var Ardhito by rememberSaveable { mutableStateOf("") }
    var average_score: Double = ((Shawn.toDoubleOrNull() ?: 0.0) + (Pete.toDoubleOrNull() ?: 0.0) + (Ardhito.toDoubleOrNull() ?: 0.0)) / 3

    var isScoreValid by rememberSaveable { mutableStateOf(true) }

    var resultbutton by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFB998E7))
                .padding(20.dp)
        ) {
            Text(
                text = "Student Score Averager", //lmao
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
                painter = painterResource(id = R.drawable.logo_uc),
                contentDescription = "UC Pride",
                modifier = Modifier.size(100.dp)
            )

            ScoreTextField(
                value = Shawn,
                onValueChanged = { Shawn = it },
                text = "Shawn's Score",
                alerttext = "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                isScoreValid = isScoreValid
            )

            ScoreTextField(
                value = Pete,
                onValueChanged = { Pete = it },
                text = "Pete's Score",
                alerttext = "Please enter a valid height greater than 0",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                isScoreValid = isScoreValid
            )

            ScoreTextField(
                value = Ardhito,
                onValueChanged = { Ardhito = it },
                text = "Ardhito's Score",
                alerttext = "Please enter a valid height greater than 0",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                isScoreValid = isScoreValid
            )

            Button(
                onClick = {
                    if (average_score < 70) {
                        Toast.makeText(
                            context,
                            "Siswa perlu diberi soal tambahan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Siswa mengerti pembelajaran",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                        resultbutton = true

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 16.dp),
                enabled = Shawn.isNotBlank() && Pete.isNotBlank() && Ardhito.isNotBlank() && isScoreValid(Shawn) && isScoreValid(Pete) && isScoreValid(Ardhito)
            ) {
                Text(text = "CALCULATE AVERAGE", fontSize = 20.sp)
            }

            if (resultbutton) {
                Text(
                    text = "Average Score : ${String.format("%.6f", average_score)}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB998E7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                        .border(1.dp, Color(0xFFB998E7), shape = RoundedCornerShape(30.dp))
                        .padding(24.dp),
                )
            }
        }
    }
}


fun isScoreValid(value: String): Boolean {
    if (value.isNotEmpty()) {
        val intValue = value.toIntOrNull()
        if (intValue != null && intValue >= 0) {
            return true
        }
    }
    return false
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    alerttext: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isScoreValid: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFB998E7), unfocusedBorderColor = Color(0xFFDA90F9)
        ),
        shape = RoundedCornerShape(30.dp),
    )

    if (!isScoreValid) {
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
fun ScorePreview() {
    StudentScore()
}
