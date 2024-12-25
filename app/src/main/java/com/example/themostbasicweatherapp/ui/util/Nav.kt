package com.example.themostbasicweatherapp.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class Nav(val route: String, val enabledIcon: ImageVector, val disabledIcon: ImageVector, val title: String) {
  CURRENT_CITY_SCREEN("cur", Icons.Filled.Favorite, Icons.Outlined.Favorite, "Мой Город"),
  ALL_CITIES_SCREEN("all", Icons.Filled.Menu, Icons.Outlined.Menu, "Другие города")
}
