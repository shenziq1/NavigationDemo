package com.github.shenziq1.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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


    @Composable
    fun Alpha(mainNavHostController: NavHostController) {
        val alphaNaviController = rememberNavController()
        Log.d("TestAlpha", alphaNaviController.toString())
        val backStackEntry by alphaNaviController.currentBackStackEntryAsState()
        val currentScreen = backStackEntry?.destination?.route ?: "alphaStart"
        Log.d("TestAlpha", backStackEntry?.destination?.route ?: "null")

        Scaffold() {
            NavHost(navController = alphaNaviController, startDestination = "alphaStart") {
                composable("alphaStart") {
                    AlphaStart(onAlphaStartClicked = {
                        alphaNaviController.navigate("alphaInner") {
                            launchSingleTop = true
                        }
                    }, mainNavHostController)
                }
                composable("alphaInner") {
                    AlphaInner(onAlphaBackClicked = {
                        alphaNaviController.navigate("alphaStart") {
                            launchSingleTop = true
                        }
                    })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun AlphaStart(onAlphaStartClicked: () -> Unit, mainNavHostController: NavHostController) {
        Scaffold(
            topBar = {
                TopAppBar() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "title")
                    }
                }
            },
            bottomBar = {
                MainBottomNavigation(navController = mainNavHostController)
            }
        ) {
            Card(onClick = onAlphaStartClicked) {
                Text(text = "alpha start")
            }
        }

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun AlphaInner(onAlphaBackClicked: () -> Unit) {
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
                            onClick = onAlphaBackClicked
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
            Card(onClick = {}) {
                Text(text = "inside alpha")
            }
        }
    }

    @Composable
    fun Beta(mainNavHostController: NavHostController) {
        Scaffold(
            bottomBar = { MainBottomNavigation(navController = mainNavHostController) }) {
            Text(text = "Beta", fontSize = 40.sp)
        }

    }

    @Composable
    fun Charlie(mainNavHostController: NavHostController) {
        Scaffold(
            bottomBar = { MainBottomNavigation(navController = mainNavHostController) }) {
            Text(text = "Charlie", fontSize = 40.sp)
        }
    }

    @Composable
    fun MainBottomNavigation(navController: NavHostController) {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Songs", "Artists", "Playlists")
        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()

        Scaffold(
        ) {
            NavHost(navController = navController, startDestination = "Songs") {
                composable(route = "Songs") {
                    Alpha(navController)
                }
                composable(route = "Artists") {
                    Beta(navController)
                }
                composable(route = "Playlists") {
                    Charlie(navController)
                }
            }
        }
    }


    //@Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        NavigationTheme {
            App()
        }
    }
}