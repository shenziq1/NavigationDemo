package com.github.shenziq1.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.shenziq1.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun Alpha() {
    val alphaNaviController = rememberNavController()
    val backStackEntry by alphaNaviController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: "alphaStart"

    Scaffold(
        topBar = {
            TopAppBar() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { alphaNaviController.navigate("alphaStart") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "navigation back"
                        )
                    }
                    Text(text = "name")
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "navigation back"
                        )
                    }
                }
            }
        },
    ) {
        NavHost(navController = alphaNaviController, startDestination = currentScreen) {
            composable("alphaStart") {
                AlphaStart(onAlphaStartClicked = {
                    alphaNaviController.navigate("alphaInner") {
                    }
                })
            }
            composable("alphaInner") {
                AlphaInner()
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlphaStart(onAlphaStartClicked: () -> Unit) {
    Card(onClick = onAlphaStartClicked) {
        Text(text = "alpha start")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlphaInner() {
    Card(onClick = {}) {
        Text(text = "inside alpha")
    }
}

@Composable
fun Beta() {
    Text(text = "Beta", fontSize = 40.sp)
}

@Composable
fun Charlie() {
    Text(text = "Charlie", fontSize = 40.sp)
}

@Composable
fun App() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Songs", "Artists", "Playlists")
//
    Scaffold(
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item)
                        }
                    )
                }
            }
        }) {
        NavHost(navController = navController, startDestination = "Songs") {
            composable(route = "Songs") {
                Alpha()
            }
            composable(route = "Artists") {
                Beta()
            }
            composable(route = "Playlists") {
                Charlie()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationTheme {
        App()
    }
}