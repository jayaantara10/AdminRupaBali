package id.jayaantara.adminrupabali.view.ui.main.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.components.MenuCardSmall
import id.jayaantara.adminrupabali.view.dev.UnderDevelopmentScreen
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.view.ui.data.dummy.FineArtCategoryItem
import id.jayaantara.adminrupabali.view.ui.data.dummy.fineArtCategoryItems
import id.jayaantara.adminrupabali.view.ui.data.static.MenuItem

@Composable
fun DashboardScreen(
    navController: NavController,
    navbarWidth: Dp
){
    UnderDevelopmentScreen()
}

@Preview(device = Devices.PIXEL_C)
@Composable
private fun DashboardScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        DashboardScreen(navController = rememberNavController(), navbarWidth = 48.dp)
    }
}