package id.jayaantara.adminrupabali.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import id.jayaantara.adminrupabali.view.ui.authentication.AuthenticationScreen
import id.jayaantara.adminrupabali.view.ui.main.MainScreen
import id.jayaantara.adminrupabali.view.ui.splash_screen.SplashScreen

@ExperimentalPagerApi
@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Route.ROOT,
        startDestination = Route.SPLASH,
    ){
        composable(
            route = Route.SPLASH
        ){
            SplashScreen(navController = navController)
        }
        
        composable(
            route = Route.AUTHENTICATION
        ){
            AuthenticationScreen(navController = navController)
        }

        composable(
            route = Route.MAIN
        ){
            MainScreen()
        }
    }
}