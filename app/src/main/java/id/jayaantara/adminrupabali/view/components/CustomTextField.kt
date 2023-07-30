package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.Validation
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography

@Composable
fun EmailTextField(
    modifier: Modifier,
    label: String,
    text: String,
    isInputError: Boolean,
    errorMessage: String,
    focusRequester: FocusRequester,
    onInputChange : (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    val color =
        if (isInputError) {
            Color.Red
        } else {
            colors.primary
        }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester),
                    label = {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1,
                                maxLines = 1,
                                text = label
                            )
                        }

                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.ic_round_email_24),
                            contentDescription = "",

                            )
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = colors.secondary,
                        disabledBorderColor = colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = colors.primaryVariant,
                        focusedLabelColor = colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = colors.primaryVariant

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = Typography.body1,
                    value = text,
                    onValueChange = onInputChange,
                )

                AnimatedVisibility(
                    visible = isInputError,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250, 0, LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            style = Typography.caption,
                            color = Color.Red,
                            text = errorMessage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier,
    label: String,
    text: String,
    isInputError: Boolean,
    errorMessage: String,
    focusRequester: FocusRequester,
    onInputChange : (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    var isPasswordVisible: Boolean by rememberSaveable { mutableStateOf(true) }

    val color =
        if (isInputError) {
            Color.Red
        } else {
            colors.primary
        }

    val trailingIcon =
        if (isPasswordVisible) {
            R.drawable.ic_round_visibility_24
        } else {
            R.drawable.ic_round_visibility_off_24
        }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester),
                    label = {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1,
                                maxLines = 1,
                                text = label
                            )
                        }

                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.ic_round_vpn_key_24),
                            contentDescription = "",

                            )
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 16.dp)
                                .size(24.dp),
                            onClick = {
                                isPasswordVisible= !isPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = trailingIcon),
                                contentDescription = "",
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = colors.secondary,
                        disabledBorderColor = colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = colors.primaryVariant,
                        focusedLabelColor = colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = colors.primaryVariant

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    visualTransformation =
                    if (isPasswordVisible){
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    textStyle = Typography.body1,
                    value = text,
                    onValueChange = onInputChange,
                )

                AnimatedVisibility(
                    visible = isInputError,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250, 0, LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            style = Typography.caption,
                            color = Color.Red,
                            text = errorMessage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShortTextField(
    modifier: Modifier,
    label: String,
    icon: Painter,
    text: String,
    isInputError: Boolean,
    errorMessage: String,
    onInputChange : (String) -> Unit,
    focusRequester: FocusRequester
) {

    val focusManager = LocalFocusManager.current

    val color =
        if (isInputError) {
            Color.Red
        } else {
            colors.primary
        }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester),
                    label = {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1,
                                maxLines = 1,
                                text = label
                            )
                        }

                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(24.dp),
                            painter = icon,
                            contentDescription = "",

                            )
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = colors.secondary,
                        disabledBorderColor = colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = colors.primaryVariant,
                        focusedLabelColor = colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = colors.primaryVariant

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = Typography.body1,
                    value = text,
                    onValueChange = onInputChange,
                )

                AnimatedVisibility(
                    visible = isInputError,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250, 0, LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            style = Typography.caption,
                            color = Color.Red,
                            text = errorMessage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NumberTextField(
    modifier: Modifier,
    label: String,
    icon: Painter,
    text: String,
    isInputError: Boolean,
    errorMessage: String,
    onInputChange : (String) -> Unit,
    focusRequester: FocusRequester
) {

    val focusManager = LocalFocusManager.current

    val color =
        if (isInputError) {
            Color.Red
        } else {
            colors.primary
        }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester),
                    label = {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1,
                                maxLines = 1,
                                text = label
                            )
                        }

                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(24.dp),
                            painter = icon,
                            contentDescription = "",

                            )
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = colors.secondary,
                        disabledBorderColor = colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = colors.primaryVariant,
                        focusedLabelColor = colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = colors.primaryVariant

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = Typography.body1,
                    value = text,
                    onValueChange = onInputChange,
                )

                AnimatedVisibility(
                    visible = isInputError,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250, 0, LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            style = Typography.caption,
                            color = Color.Red,
                            text = errorMessage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LongTextField(
    modifier: Modifier,
    label: String,
    text: String,
    isInputError: Boolean,
    errorMessage: String,
    onInputChange : (String) -> Unit,
    focusRequester: FocusRequester
) {

    val focusManager = LocalFocusManager.current

    val color =
        if (isInputError) {
            Color.Red
        } else {
            colors.primary
        }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester),
                    label = {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1,
                                text = label
                            )
                        }

                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = colors.secondary,
                        disabledBorderColor = colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = colors.primaryVariant,
                        focusedLabelColor = colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = colors.primaryVariant

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = Typography.body1,
                    value = text,
                    onValueChange = onInputChange,
                )

                AnimatedVisibility(
                    visible = isInputError,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250, 0, LinearEasing)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp, start = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            style = Typography.caption,
                            color = Color.Red,
                            text = errorMessage
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CustomTextFieldPreview(){
    AdminRupaBaliTheme(darkTheme = false) {
        val context = LocalContext.current

        val emailTextFieldLabel = stringResource(id = R.string.label_email)
        var emailTextFieldValue: String by rememberSaveable { mutableStateOf("") }
        var isEmailTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
        var emailTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
        val emailTextFieldFocusRequester = FocusRequester()

        Surface(
        ) {
            EmailTextField(
                modifier = Modifier
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp),
                label = emailTextFieldLabel,
                text = emailTextFieldValue,
                isInputError = isEmailTextFieldError,
                errorMessage = emailTextFieldErrorMessage,
                focusRequester = emailTextFieldFocusRequester,
                onInputChange = {
                    emailTextFieldValue = it
                    if (Validation.isEmpty(it)) {
                        isEmailTextFieldError= true
                        emailTextFieldErrorMessage = context.getString(R.string.error_empty_email)
                    } else {
                        isEmailTextFieldError = false
                    }
                },
            )
        }
    }
}
