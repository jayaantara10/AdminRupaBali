package id.jayaantara.adminrupabali.view.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.jayaantara.adminrupabali.view.Route
import id.jayaantara.adminrupabali.view.ui.authentication.AuthenticationScreen
import id.jayaantara.adminrupabali.view.ui.main.admin.AdminManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.artwork.ArtworkManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.artworkcategory.ArtworkCategoryManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.dashboard.DashboardScreen
import id.jayaantara.adminrupabali.view.ui.main.event.EventManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.fineartcategory.FineArtCategoryManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.news.NewsManagementScreen
import id.jayaantara.adminrupabali.view.ui.main.user.UserManagementScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    navbarWidth: Dp
) {
    NavHost(
        navController = navController,
        route = Route.MAIN,
        startDestination = MainRoute.DASHBOARD,
    ) {
        composable(
            route = MainRoute.DASHBOARD
        ){
            DashboardScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.ADMIN_MANAGEMENT
        ){
            AdminManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.USER_MANAGEMENT
        ){
            UserManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.ARTWORK_MANAGEMENT
        ){
            ArtworkManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.FINE_ART_CATEGORY_MANAGEMENT
        ){
            FineArtCategoryManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.ARTWORK_CATEGORY_MANAGEMENT
        ){
            ArtworkCategoryManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.NEWS_MANAGEMENT
        ){
            NewsManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }

        composable(
            route = MainRoute.EVENT_MANAGEMENT
        ){
            EventManagementScreen(navController = navController, navbarWidth = navbarWidth)
        }
    }
}