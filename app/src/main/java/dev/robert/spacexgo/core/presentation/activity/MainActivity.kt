package dev.robert.spacexgo.core.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.spacexgo.core.presentation.common.BottomNavItem
import dev.robert.spacexgo.features.NavGraphs
import dev.robert.spacexgo.features.destinations.*
import dev.robert.spacexgo.core.presentation.theme.SpaceXGoTheme
import dev.robert.spacexgo.core.presentation.theme.contentColor
import dev.robert.spacexgo.core.presentation.theme.darkGrey
import dev.robert.spacexgo.core.presentation.theme.lightBlue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXGoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val showBottomNav: Boolean
                    val navController = rememberNavController()
                    val navHostEngine = rememberNavHostEngine()
                    val newNavBackStackEntry by navController.currentBackStackEntryAsState()
                    val route = newNavBackStackEntry?.destination?.route

                    val bottomNavItems = listOf(
                        BottomNavItem.Rockets,
                        BottomNavItem.Upcoming,
                        BottomNavItem.Ships,
                        BottomNavItem.Capsules,
                        BottomNavItem.Company,
                    )

                    showBottomNav = route in listOf(
                        RocketsScreenDestination.route,
                        LaunchesScreenDestination.route,
                        ShipsScreenDestination.route,
                        CapsulesScreenDestination.route,
                        CompanyScreenDestination.route,
                    )
                    Scaffold(
                        bottomBar = {
                            if (showBottomNav) {
                                NavigationBar(
                                    containerColor = darkGrey,
                                    contentColor = contentColor,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .shadow(
                                            shape = RoundedCornerShape(15.dp),
                                            clip = true,
                                            elevation = 16.dp
                                        ),
                                ) {

                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentDestination = navBackStackEntry?.destination
                                    bottomNavItems.forEach { item ->
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = null,
                                                )
                                            },
                                            label = {
                                                Text(text = item.label, overflow = TextOverflow.Ellipsis, fontSize = 10.sp)
                                            },
                                            alwaysShowLabel = true,
                                            selected = currentDestination?.route?.contains(item.destination.route) == true,

                                            onClick = {
                                                navController.navigate(item.destination.route) {
                                                    navController.graph.startDestinationRoute?.let { screenRoute ->
                                                        popUpTo(screenRoute) {
                                                            saveState = true
                                                        }
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) { values ->
                        Box(modifier = Modifier.padding(values)){
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = navController,
                                engine = navHostEngine
                            )
                        }
                    }

                }
            }
        }
    }
}

