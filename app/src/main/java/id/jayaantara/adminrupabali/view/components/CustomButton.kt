package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography

@Composable
fun PositiveIconButtonFlex(
    modifier: Modifier,
    icon: Int,
    isVisible: Boolean,
    isEnable: Boolean,
    onClick: (() -> Unit)
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0.4f,
            ),
            exit = fadeOut(
                animationSpec = tween( 250, 0, LinearEasing)
            )
        ) {
            Button(
                onClick = onClick,
                enabled = isEnable,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.primary)

            ) {
                Icon(
                    painter = painterResource(
                        id = icon),
                    contentDescription = "",
                    tint = colors.background
                )
            }
        }
    }
}

@Composable
fun PositiveIconButton(
    modifier: Modifier,
    icon: Int,
    isVisible: Boolean = true,
    isEnable: Boolean = true,
    onClick: (() -> Unit)
) {
    Box(
        modifier = modifier
            .height(56.dp)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0.4f,
            ),
            exit = fadeOut(
                animationSpec = tween( 250, 0, LinearEasing)
            )
        ) {
            Button(
                onClick = onClick,
                enabled = isEnable,
                modifier = Modifier
                    .size(56.dp)
                    .padding(0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.primary)

            ) {
                Icon(painter = painterResource(
                    id = icon),
                    contentDescription = "",
                    tint = colors.background
                )
            }
        }
    }
}

@Composable
fun PositiveTextButtonSmall(
    modifier: Modifier,
    text: String,
    isVisible: Boolean = true,
    isEnable: Boolean = true,
    onClick: (() -> Unit)
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0.4f,
            ),
            exit = fadeOut(
                animationSpec = tween( 250, 0, LinearEasing)
            )
        ) {
            Button(
                onClick = onClick,
                enabled = isEnable,
                modifier = Modifier
                    .padding(0.dp)
                    .height(56.dp)
                    .widthIn(min = 96.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.primary)

            ) {
                Text(
                    style = Typography.button,
                    color = colors.background,
                    text = text)

            }
        }
    }
}

@Composable
fun PositiveTextButtonFlex(
    modifier: Modifier,
    text: String,
    isVisible: Boolean = true,
    isEnable: Boolean = true,
    onClick: (() -> Unit)
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0.4f,
            ),
            exit = fadeOut(
                animationSpec = tween( 250, 0, LinearEasing)
            )
        ) {
            Button(
                onClick = onClick,
                enabled = isEnable,
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.primary)

            ) {
                Text(
                    style = Typography.button,
                    color = colors.background,
                    text = text)

            }
        }
    }
}

@Composable
fun NegativeTextButtonFlex(
    modifier: Modifier,
    text: String,
    isVisible: Boolean = true,
    isEnable: Boolean = true,
    onClick: (() -> Unit)
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                initialAlpha = 0.4f,
            ),
            exit = fadeOut(
                animationSpec = tween( 250, 0, LinearEasing)
            )
        ) {
            OutlinedButton(
                onClick = onClick,
                enabled = isEnable,
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = colors.primary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    style = Typography.button,
                    color = colors.primary,
                    text = text)

            }
        }
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        val loginButtonLabel = stringResource(id = R.string.label_login)
        val isLoginButtonVisible: Boolean by remember { mutableStateOf(true ) }
        val isLoginButtonEnable: Boolean by remember { mutableStateOf(true ) }
        PositiveTextButtonFlex(
            modifier = Modifier
                .padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 16.dp)
                .wrapContentHeight(),
            text = loginButtonLabel ,
            isVisible = isLoginButtonVisible,
            isEnable = isLoginButtonEnable
        ){}
    }
}