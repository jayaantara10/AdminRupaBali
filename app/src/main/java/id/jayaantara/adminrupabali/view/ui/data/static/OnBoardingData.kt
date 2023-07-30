package id.jayaantara.adminrupabali.view.ui.data.static

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import id.jayaantara.adminrupabali.R

data class OnBoardingItem(
    val image: Int,
    val title: String,
    val description: String
)

@Composable
fun AddDataOnBoarding() = listOf(
    OnBoardingItem(
        image = R.drawable.gwk,
        title = stringResource(R.string.onboarding_title_1),
        description = stringResource(R.string.onboarding_description_1)
    ),

    OnBoardingItem(
        image = R.drawable.lukisan,
        title = stringResource(R.string.onboarding_title_2),
        description = stringResource(R.string.onboarding_description_2)
    ),

    OnBoardingItem(
        image = R.drawable.seniman,
        title = stringResource(R.string.onboarding_title_3),
        description = stringResource(R.string.onboarding_description_3)
    )
)