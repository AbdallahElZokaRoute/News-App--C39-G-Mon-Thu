package com.route.newsapp_c39_gmonthu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.route.newsapp_c39_gmonthu.fragments.CategoriesFragment
import com.route.newsapp_c39_gmonthu.fragments.news.NewsFragmentContent
import com.route.newsapp_c39_gmonthu.ui.theme.NewsAppC39GMonThuTheme
import com.route.newsapp_c39_gmonthu.utils.NewsAppBar
import com.route.newsapp_c39_gmonthu.utils.NewsDrawerSheet
import kotlinx.coroutines.launch

// CTRL + ALT + o
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC39GMonThuTheme {
                // A surface container using the 'background' color from the theme
                NewsScreen()
            }
        }
    }
    // 30 FPS <->  Slack <-> Discord
}
// 1- Refactoring
// 2- APIs & Networking
// Categories Fragment +Navigation Component +HTTP Logging Interceptor

//Search + Settings + News Details Activity
// Sun & Wed ->Categories Fragment +Navigation Component + Logging Interceptor
@Composable
fun NewsScreen() {
    // Coordinator Layout
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    ModalNavigationDrawer(drawerContent = {
        NewsDrawerSheet(onSettingsClick = {}, onCategoriesClick = {
            navController.popBackStack()
            if (navController.currentDestination?.route != CategoriesFragmentScreen.ROUTE_NAME)
                navController.navigate(CategoriesFragmentScreen.ROUTE_NAME)
            scope.launch {
                drawerState.close()
            }
        })
    }, drawerState = drawerState) {
        Scaffold(topBar = {
            NewsAppBar {
                scope.launch {
                    drawerState.open()
                }
            }
        }) { paddingValues ->
//            NewsFragmentContent(Modifier.padding(top = paddingValues.calculateTopPadding()))
            NavHost(
                navController = navController,
                startDestination = CategoriesFragmentScreen.ROUTE_NAME,
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                composable(CategoriesFragmentScreen.ROUTE_NAME) {
                    CategoriesFragment(navHostController = navController)
                }
                composable(
                    "${NewsFragmentScreen.ROUTE_NAME}/{category_id}",
                    arguments = listOf(navArgument("category_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val categoryId = navBackStackEntry.arguments?.getString("category_id")
                    NewsFragmentContent(categoryId = categoryId ?: "")
                }
                composable(SettingsFragmentScreen.ROUTE_NAME) {
                    // SettingsScreen as Composable


                }
            }
        }
    }

}


//@Preview
//@Composable
//fun NewsListPreview() {
//    NewsList()
//}


@Preview
@Composable
fun NewsScreenPreview() {
    NewsScreen()
}
