package id.jayaantara.adminrupabali.view.dev

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.Typography

@Composable
fun UnderDevelopmentScreen(){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background
            )
    ) {

        // References
        val (
            logoRupaBaliIcon,
            messageText
        ) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(logoRupaBaliIcon){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rupa_bali_logo_03),
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(messageText) {
                    top.linkTo(logoRupaBaliIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 32.dp),
            style = Typography.body1,
            maxLines = 2,
            text = stringResource(id = R.string.under_development_page)
        )
    }
}

@Preview(device = Devices.PIXEL_C)
@Composable
private fun UnderDevelopmentScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        UnderDevelopmentScreen()
    }
}