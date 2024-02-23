package com.camilo.test

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camilo.test.ui.theme.MidtermTestCamiloMeliTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

//MidtermTestCamiloMeliTheme
//301240077
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val context = LocalContext.current
    val store = CityDataStore(context)

    // State for input fields
    var name by remember { mutableStateOf("") }
    var userid by remember { mutableStateOf("077") }
    var email by remember { mutableStateOf("") }

    var savedData by remember { mutableStateOf("") }


    var cityName by remember { mutableStateOf("") }
    var cityDate by remember { mutableStateOf("") }
    var cityTemperature by remember { mutableStateOf("") }
    var cityDescription by remember { mutableStateOf("") }

    Text(text = "MAPD721 – Samsung Advanced Android Development TEST", fontWeight = FontWeight.Bold)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {

        // Input fields
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("City Name") }
        )
        OutlinedTextField(
            value = cityDate,
            onValueChange = { cityDate = it },
            label = { Text("Date") }
        )
        OutlinedTextField(
            value = cityTemperature,
            onValueChange = { cityTemperature = it },
            label = { Text("Temperature") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = cityDescription,
            onValueChange = { cityDescription = it },
            label = { Text("Description") }
        )

        // Buttons options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { saveData(store, cityName, cityDate, cityTemperature,cityDescription,context) }) {
                Text("Save")
            }
            Button(onClick = { loadData(context,store) { data ->
                savedData = data
                showToast(context, "Data loaded (0077)" )

            } }) {
                Text("Load")
            }
            Button(onClick = { clearData(store) }) {
                Text("Clear")
            }
        }

        // Display saved data
        Text("Saved Data: $savedData")
        Spacer(modifier = Modifier.height(300.dp))
        Divider()
        Text(text = "301240077", fontWeight = FontWeight.Bold)
        Text(text = "Camilo Meli", fontWeight = FontWeight.Bold)
    }

}

private fun saveData(store: CityDataStore, cityName: String, cityDate: String, cityTemperature: String, cityDescription: String, context: Context) {
    MainScope().launch {
        store.saveCityData(cityName,cityDate,cityTemperature,cityDescription)
        // Show a notification message when data is saved
        Toast.makeText(context, "Data saved (0077)", Toast.LENGTH_SHORT).show()
    }
}

private fun loadData(context: Context,store: CityDataStore, updateSavedData: (String) -> Unit) {
    MainScope().launch {
        store.getSavedData.collect { data ->
            updateSavedData(data)
        }
       // Toast.makeText(context, "Load data (0077)", Toast.LENGTH_SHORT).show()
    }
}

fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    MainScope().launch {
        Toast.makeText(context, message, duration).show()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


private fun clearData(store: CityDataStore) {
    MainScope().launch {
        store.clearUserData()
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MidtermTestCamiloMeliTheme {
        Greeting("Android")
    }
}
