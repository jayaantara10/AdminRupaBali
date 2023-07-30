package id.jayaantara.adminrupabali.view.ui.splash_screen

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import kotlinx.coroutines.delay
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.InternetUtils
import id.jayaantara.adminrupabali.common.toAuthenticationScreen
import id.jayaantara.adminrupabali.common.toMainActivity
import id.jayaantara.adminrupabali.view.components.InformationAlertDialog

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
){

    //LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    //Activity
    val activity = (LocalContext.current as? Activity)

    //Context
    val context= LocalContext.current

    // Connection check
    val isConnect = InternetUtils.isOnline(context)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background
            )
    ) {

        // References
        val (
            logoRupaBaliIcon
        ) = createRefs()

        //AlertDialog State
        var infoDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
        var infoDialogTitle: String by rememberSaveable { mutableStateOf("") }
        var infoDialogDescription: String by rememberSaveable { mutableStateOf("") }

        if(!isConnect){
            infoDialogTitle = stringResource(id = R.string.label_alert_title_connection_problem)
            infoDialogDescription = stringResource(id = R.string.label_alert_message_no_connection)
        }

        Box(
            modifier = Modifier
                .constrainAs(logoRupaBaliIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(150.dp),
        ) {
            var startAnimation by remember { mutableStateOf(false) }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 3000
                )
            )

            LaunchedEffect(key1 = true) {
                startAnimation = true
                delay(4000)

                // Check COnnection
                if (isConnect){
                    //Get Local Auth
                    viewModel.localAuthState.observe(lifecycleOwner) { resource ->
                        if (resource.isNullOrBlank() || resource.isEmpty()) {
                            toAuthenticationScreen(navController = navController)
                        } else {
                            toMainActivity(navController = navController)
                        }
                    }
                }else {
                    infoDialogState = true
                }
            }

            Icon(
                modifier = Modifier
                    .alpha(alpha = alphaAnim.value)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.rupa_bali_logo_03),
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
        }

        InformationAlertDialog(
            title = infoDialogTitle,
            description = infoDialogDescription,
            dialogState = infoDialogState,
            onDismissRequest = {
                infoDialogState = !it
                activity?.finish()
            },
        )
    }
}

@Preview(device = Devices.PIXEL_C)
@Composable
private fun SplashScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        SplashScreen(navController = rememberNavController())
    }
}