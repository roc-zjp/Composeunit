package com.zjp.compose_unit.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.common.compose.CustomIndicator
import com.zjp.compose_unit.R
import com.zjp.compose_unit.common.LocalThemeColor
import com.zjp.compose_unit.common.colorBlue
import com.zjp.compose_unit.route.HomeSections
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.route.unitNavGraph
import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.compose_unit.ui.theme.LocalFont
import com.zjp.compose_unit.ui.theme.fontMap
import com.zjp.compose_unit.ui.theme.local
import kotlinx.coroutines.launch

@Composable
fun App() {
    val navController = rememberNavController()
    var context = LocalContext.current
    var scope = rememberCoroutineScope()


    var themeColor by remember {
        mutableStateOf(getThemeColor(context))
    }

    var currentFont by remember {
        mutableStateOf(getFont(context))
    }


    val onThemeColorChange: OnThemeColorChange = { color ->
        themeColor = color
        scope.launch { saveThemeColor(context, color) }
    }

    val onFontChange: OnFontChange = { font ->
        currentFont = font
        scope.launch { saveFont(context, font) }
    }



    CompositionLocalProvider(
        LocalThemeColor provides themeColor,
        LocalFont provides currentFont,
    ) {
        Compose_unitTheme(primary = themeColor, font = fontMap[currentFont] ?: local) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController,
                        tabs = arrayOf(HomeSections.COMPOSE, HomeSections.PROFILE)
                    ) {
                        navController.navigate(it.route) {
                            popUpTo(HomeSections.COMPOSE.route)
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigate(Screen.Debug.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.debug),
                            contentDescription = "debug"
                        )
                    }
                }
            ) { innerPaddingModifier ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                    modifier = Modifier
                        .padding(innerPaddingModifier)

                ) {
                    unitNavGraph(navController, onThemeColorChange, onFontChange)
                }

            }
        }
    }

}




typealias OnThemeColorChange = (color: Color) -> Unit
typealias OnFontChange = (fontStr: String) -> Unit

@Composable
fun BottomBar(
    navController: NavController,
    tabs: Array<HomeSections> = HomeSections.values(),
    onTabChange: (HomeSections) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Screen.Splash.route
    val routes = remember { tabs.map { it.route } }

    if (currentRoute in routes) {
        ProvideWindowInsets() {
            val navPaddingValues =
                rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars)
            BottomAppBar(Modifier.padding(navPaddingValues)) {
                TabRow(
                    selectedTabIndex = routes.indexOf(currentRoute),
                    indicator = {
                        CustomIndicator(it, selectIndex = routes.indexOf(currentRoute))
                    },
                    divider = {},
                ) {
                    tabs.forEachIndexed { _, homeSection ->
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable(
                                    onClick = {
                                        onTabChange(homeSection)
                                    },
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                )
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = homeSection.icon),
                                contentDescription = null
                            )
                            if (currentRoute == homeSection.route) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = homeSection.title.uppercase().substring(0..2))
                            }
                        }

                    }
                }
            }
        }
    }
}


private suspend fun saveThemeColor(context: Context, color: Color) {
    context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).edit()
        .putInt("themeColor", color.toArgb()).commit()

}

private fun getThemeColor(context: Context): Color {
    var colorInt =
        context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).getInt(
            "themeColor",
            colorBlue.toArgb()
        )
    return Color(colorInt)
}


private suspend fun saveFont(context: Context, fontStr: String) {
    context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).edit()
        .putString("font", fontStr).commit()
}

private fun getFont(context: Context): String {
    return context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE)
        .getString("font", "") ?: "local"
}



