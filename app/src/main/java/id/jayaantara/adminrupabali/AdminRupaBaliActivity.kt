package id.jayaantara.adminrupabali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import id.jayaantara.adminrupabali.view.RootNavGraph
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminRupaBaliActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(500)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContent {
            AdminRupaBaliTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    navController = rememberNavController()
                    RootNavGraph(navController = navController)
                }
            }
        }
    }
}