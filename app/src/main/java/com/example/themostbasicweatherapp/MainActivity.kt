package com.example.themostbasicweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.themostbasicweatherapp.ui.components.ChangeCityWidget
import com.example.themostbasicweatherapp.ui.screens.AllCitiesScreen
import com.example.themostbasicweatherapp.ui.screens.CurCityScreen
import com.example.themostbasicweatherapp.ui.theme.TheMostBasicWeatherAppTheme
import com.example.themostbasicweatherapp.ui.util.Nav
import com.example.themostbasicweatherapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    val mainVM = getViewModel<MainViewModel>()
    setContent {
      var changeCityOpen by remember { mutableStateOf(false) }
      val navCtrl = rememberNavController()
      TheMostBasicWeatherAppTheme {
        if (changeCityOpen) {
          ChangeCityWidget(
              onDismissRequest = { changeCityOpen = false },
              city = mainVM.state.collectAsState().value.city,
              onConfirmation = {
                mainVM.changeCity(it)
                changeCityOpen = false
              } // TODO вьюмодель не меняет город
              // TODO сделать самообновление либо pull-to-refresh
              )
        }
        val current = navCtrl.currentBackStackEntryAsState().value?.destination?.route
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
              BottomAppBar() {
                Nav.entries.forEach {
                  val selected = current == it.route
                  NavigationBarItem(
                      selected,
                      icon = { Icon(if (selected) it.enabledIcon else it.disabledIcon, null) },
                      onClick = { navCtrl.navigate(it.route) })
                }
              }
            },
            topBar = {
              Nav.entries
                  .find { it.route == current }
                  ?.let { CenterAlignedTopAppBar(title = { Text(it.title) }) }
            }) { innerPadding ->
              NavHost(
                  modifier = Modifier.padding(innerPadding),
                  navController = navCtrl,
                  startDestination = Nav.CURRENT_CITY_SCREEN.route) {
                    composable(Nav.CURRENT_CITY_SCREEN.route) {
                      CurCityScreen(mainVM) { changeCityOpen = true }
                    }
                    composable(Nav.ALL_CITIES_SCREEN.route) { AllCitiesScreen() }
                  }
            }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  TheMostBasicWeatherAppTheme { Greeting("Android") }
}
