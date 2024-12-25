package com.example.themostbasicweatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ChangeCityWidget(onDismissRequest: () -> Unit, onConfirmation: (String) -> Unit, city: String) {

  var newCity by remember { mutableStateOf(city) }

  Dialog(onDismissRequest = { onDismissRequest() }) {
    Card(
        modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Поменять основной город", textAlign = TextAlign.Center)
        OutlinedTextField(
            value = newCity, onValueChange = { newCity = it }, label = { Text("Город") })
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          TextButton(onClick = onDismissRequest) { Text("Отменить") }
          Button(
            onClick = { onConfirmation(newCity) },
            modifier = Modifier.weight(1f))
          { Text("Создать") }
        }
      }
    }
  }
}
