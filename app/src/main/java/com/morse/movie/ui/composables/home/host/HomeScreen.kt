package com.morse.movie.ui.composables.home.host

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.morse.movie.app.Directions
import com.morse.movie.app.HomeCoordinator
import com.morse.movie.data.entities.ui.TabItem
import com.morse.movie.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalUnitApi::class)
@Composable
fun HomeScreen(controller: NavHostController? = null) {
    val scaffoldState = rememberScaffoldState()
    val tabsController = rememberNavController()
    Scaffold(
        bottomBar = {
            MaoBottomNavigation(controller = tabsController)
        },
        scaffoldState = scaffoldState
    ) { HomeCoordinator(controller = tabsController) }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun MaoBottomNavigation(controller: NavHostController?) {
    BottomNavigation(elevation = 5.dp) {
        val navBackStackEntry by controller!!.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        TabItem.tabs().forEach { currentTab ->
            val isSelected =
                currentDestination?.route == currentTab.routeName
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    Directions.findDirection(currentTab.routeName).navigate(controller)
                },
                enabled = true,
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(id = if (isSelected) currentTab.selectedIcon else currentTab.unselectedIcon),
                        contentDescription = currentTab.routeName
                    )
                },
                modifier = Modifier.background(Color.White),
                label = {
                    Text(
                        text = stringResource(id = currentTab.label),
                        style = Typography.body1,
                        color = if (isSelected) Color(0xFFE24951) else Color(0xff999999),
                        fontSize = TextUnit(10F, TextUnitType.Sp),
                        textAlign = TextAlign.Center,
                    )
                },
                selectedContentColor = Color(0xFFE24951),
                unselectedContentColor = Color(0xff999999)
            )
        }
    }
}