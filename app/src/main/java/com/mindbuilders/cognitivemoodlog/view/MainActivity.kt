package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.R
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import com.mindbuilders.cognitivemoodlog.ui.theme.Purple700
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CognitiveMoodLogTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.MainMenu.route) {
                    composable(Screen.MainMenu.route) { MainMenu(navController = navController) }
                    composable(Screen.DescribeSituation.route) { DescribeSituation(navController = navController) }
                }
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

@Composable
fun MainMenu(navController: NavController) {
    Scaffold(
        topBar = { CbtBar() }
    ) {


        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                mainMenuDestinations.forEach { screen ->
                    MenuButton(stringResource(id = screen.resourceId)) {
                        navController.navigate(
                            screen.route
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(name: String, navigate: () -> Unit) {
    Button(navigate) {
        Text(text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    CognitiveMoodLogTheme {
        MainMenu(navController = navController)
    }
}

//todo Put somewhere better
sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object MainMenu : Screen("mainMenu", R.string.title_activity_main)
    object DescribeSituation : Screen("describe_situation", R.string.describe_situation)
}

val mainMenuDestinations = listOf(
    Screen.DescribeSituation
)

@Composable
fun CbtBar() {
    TopAppBar(title = { Text("Top AppBar") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            Icon(
                Icons.Default.Menu,
                "menuButton",
                modifier = Modifier.clickable {
                    // scaffoldState.drawerState.open()
                }
            )
        })
}