package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.view.ui.data.dummy.artworkCategoryItems

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableDetails(
    modifier: Modifier,
    title: String,
    textDetails: String,
    isExpanded: Boolean,
    onExpandClick: (() -> Unit)
){

    Box(
        modifier = modifier
    ){
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
        ){
            val (
                titleText,
                expandButton,
                detailText,
                bottomDivider,
            ) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(titleText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(expandButton.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 16.dp),
                style = Typography.h2,
                maxLines = 1,
                text = title
            )

            Button(
                modifier = Modifier
                    .constrainAs(expandButton) {
                        top.linkTo(titleText.top)
                        bottom.linkTo(titleText.bottom)
                        end.linkTo(parent.end)
                    }
                    .size(28.dp)
                    .padding(0.dp),
                onClick = onExpandClick,
                contentPadding = PaddingValues(4.dp),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary)

            ) {
                Icon(
                    painter = painterResource(
                        id = if (isExpanded) R.drawable.ic_round_expand_less_24 else R.drawable.ic_round_expand_more_24
                    ),
                    contentDescription = "",
                    tint = MaterialTheme.colors.background
                )
            }

            Box(
                modifier = Modifier
                    .constrainAs(detailText) {
                        top.linkTo(titleText.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 16.dp),
            ){
                AnimatedContent(
                    targetState = isExpanded,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(150, 150)) with
                                fadeOut(animationSpec = tween(150)) using
                                SizeTransform { initialSize, targetSize ->
                                    if (targetState) {
                                        keyframes {
                                            // Expand horizontally first.
                                            IntSize(targetSize.width, initialSize.height) at 150
                                            durationMillis = 300
                                        }
                                    } else {
                                        keyframes {
                                            // Shrink vertically first.
                                            IntSize(initialSize.width, targetSize.height) at 150
                                            durationMillis = 300
                                        }
                                    }
                                }
                    }
                ) { targetExpanded ->
                    if (targetExpanded) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            style = Typography.body1,
                            textAlign = TextAlign.Justify,
                            text = textDetails
                        )
                    }
                }
            }

            Divider(
                modifier = Modifier
                    .constrainAs(bottomDivider) {
                        top.linkTo(detailText.bottom)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
                color = MaterialTheme.colors.primary, thickness = 2.dp
            )

        }

    }
}

@Preview
@Composable
private fun ExpandableViewPreview() {
    AdminRupaBaliTheme(darkTheme = false) {

        var isExpanded: Boolean by rememberSaveable { mutableStateOf(true) }

        ExpandableDetails(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.description_title),
            textDetails = artworkCategoryItems[0].description,
            isExpanded = isExpanded,
            onExpandClick = { isExpanded = !isExpanded}
        )
    }
}