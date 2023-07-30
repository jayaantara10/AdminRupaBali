package id.jayaantara.adminrupabali.common

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import id.jayaantara.adminrupabali.AdminRupaBaliActivity
import id.jayaantara.adminrupabali.view.Route

fun toAuthenticationScreen(navController: NavController) {
    navController.popBackStack()
    navController.navigate(Route.AUTHENTICATION)
}

fun toMainActivity(navController: NavController) {
    navController.popBackStack()
    navController.navigate(Route.MAIN)
}

fun backToRoot(context: Context){
    context.startActivity(Intent(context, AdminRupaBaliActivity::class.java))
}