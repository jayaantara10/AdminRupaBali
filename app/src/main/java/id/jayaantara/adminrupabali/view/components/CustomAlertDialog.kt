package id.jayaantara.adminrupabali.view.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.R

@Composable
fun InformationAlertDialog(
    title: String,
    description: String,
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit
){

    if (dialogState) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.background,
            onDismissRequest = {
                onDismissRequest(dialogState)
            },
            title = null,
            text = null,
            buttons = {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // References
                    val (
                        titleText,
                        descriptionText,
                        okButton,
                    ) = createRefs()

                    // Title Text
                    Text(
                        modifier = Modifier
                            .constrainAs(titleText) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        style = Typography.h3,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        text = title
                    )

                    // Description Text
                    Text(
                        modifier = Modifier
                            .constrainAs(descriptionText) {
                                top.linkTo(titleText.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp),
                        style = Typography.body2,
                        textAlign = TextAlign.Left,
                        maxLines = 5,
                        text = description
                    )

                    // Dismis Button
                    Box(
                        modifier = Modifier
                            .constrainAs(okButton) {
                                top.linkTo(descriptionText.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp)
                            .wrapContentSize()
                    ) {
                        Button(
                            onClick = {
                                      onDismissRequest(dialogState)
                            },
                            enabled = true,
                            modifier = Modifier
                                .height(40.dp)
                                .width(72.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary)

                        ) {
                            Text(
                                style = Typography.button,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.background,
                                text = stringResource(id = R.string.label_ok)
                            )
                        }
                    }
                }

            },
            shape = RoundedCornerShape(12.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        )
    }
}

@Composable
fun ConfirmationAlertDialog(
    title: String,
    description: String,
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit,
    onConfirmButtonClick: () -> Unit
){

    if (dialogState) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.background,
            onDismissRequest = {
                onDismissRequest(dialogState)
            },
            title = null,
            text = null,
            buttons = {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // References
                    val (
                        titleText,
                        descriptionText,
                        noButton,
                        yesButton
                    ) = createRefs()

                    // Title Text
                    Text(
                        modifier = Modifier
                            .constrainAs(titleText) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        style = Typography.h3,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        text = title
                    )

                    // Description Text
                    Text(
                        modifier = Modifier
                            .constrainAs(descriptionText) {
                                top.linkTo(titleText.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp),
                        style = Typography.body2,
                        textAlign = TextAlign.Left,
                        maxLines = 5,
                        text = description
                    )

                    // No Button
                    Box(
                        modifier = Modifier
                            .constrainAs(noButton) {
                                top.linkTo(descriptionText.bottom)
                                end.linkTo(yesButton.start)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp)
                            .wrapContentSize()
                    ) {
                        OutlinedButton(
                            onClick = {
                                onDismissRequest(dialogState)
                            },
                            enabled = true,
                            modifier = Modifier
                                .height(40.dp)
                                .width(72.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = colors.primary),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            )

                        ) {
                            Text(
                                style = Typography.button,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.primary,
                                text = stringResource(id =R.string.label_no )
                            )
                        }
                    }

                    // Yes Button
                    Box(
                        modifier = Modifier
                            .constrainAs(yesButton) {
                                top.linkTo(descriptionText.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp, start = 16.dp)
                            .wrapContentSize()
                    ) {
                        Button(
                            onClick = onConfirmButtonClick,
                            enabled = true,
                            modifier = Modifier
                                .height(40.dp)
                                .width(72.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary)

                        ) {
                            Text(
                                style = Typography.button,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.background,
                                text = stringResource(id = id.jayaantara.adminrupabali.R.string.label_yes)
                            )
                        }
                    }

                }

            },
            shape = RoundedCornerShape(12.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        )
    }
}

@Composable
fun CustomInputDialog(
    modifier: Modifier,
    title: String,
    inputLayout: @Composable () -> Unit,
    dialogState: Boolean,
    dismissButtonLabel: String,
    onDismissRequest: (dialogState: Boolean) -> Unit,
    confirmationButtonLabel: String,
    onConfirmButtonClick: () -> Unit
){

    if (dialogState) {
        val localDensity = LocalDensity.current

        var dialogHeightDp by remember {
            mutableStateOf(0.dp)
        }

        AlertDialog(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            onDismissRequest = {
                onDismissRequest(dialogState)
            },
            title = null,
            text = null,
            buttons = {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // References
                    val (
                        titleText,
                        inputLayout,
                        noButton,
                        yesButton
                    ) = createRefs()

                    // Title Text
                    Text(
                        modifier = Modifier
                            .constrainAs(titleText) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        style = Typography.h3,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        text = title
                    )

                    Box(
                        modifier = Modifier
                            .constrainAs(inputLayout) {
                                top.linkTo(titleText.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .wrapContentHeight()
                            .padding(top = 16.dp)
                    ){
                        inputLayout()
                    }

                    // Confirm Button
                    Box(
                        modifier = Modifier
                            .constrainAs(yesButton) {
                                top.linkTo(inputLayout.bottom)
                                end.linkTo(noButton.start)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp)
                            .wrapContentSize()
                    ) {
                        Button(
                            onClick = onConfirmButtonClick,
                            enabled = true,
                            modifier = Modifier
                                .height(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary)

                        ) {
                            Text(
                                style = Typography.button,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.background,
                                text = confirmationButtonLabel
                            )
                        }
                    }

                    // Dismiss Button
                    Box(
                        modifier = Modifier
                            .constrainAs(noButton) {
                                top.linkTo(inputLayout.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 16.dp, start = 16.dp)
                            .wrapContentSize()
                    ) {
                        OutlinedButton(
                            onClick = {
                                onDismissRequest(dialogState)
                            },
                            enabled = true,
                            modifier = Modifier
                                .height(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = colors.primary),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            )

                        ) {
                            Text(
                                style = Typography.button,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.primary,
                                text = dismissButtonLabel
                            )
                        }
                    }
                }

            },
            shape = RoundedCornerShape(12.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        )
    }
}

@Preview
@Composable
fun AlertDialogPreview() {
    AdminRupaBaliTheme(darkTheme = true) {
        var dialogState: Boolean by rememberSaveable{ mutableStateOf(true) }
        ConfirmationAlertDialog(
            title = "Test App Bar",
            description = "Its test for appbar Its test for appbar Its test for appbar",
            dialogState = dialogState,
            onDismissRequest = {
                dialogState = !it
            },
            onConfirmButtonClick = {}
        )
    }
}