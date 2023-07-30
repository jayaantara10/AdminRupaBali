package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography

data class OptionItem(
    val id: Int,
    val option: String,
)

@Composable
fun SimpleOptionSelector(
    modifier: Modifier,
    icon: Painter,
    label: String,
    selectedOptionId: Int,
    options: List<OptionItem>,
    isInputError: Boolean,
    errorMessage: String,
    focusRequester: FocusRequester,
    onOptionSelected: (Int) -> Unit,
){
    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val focusManager = LocalFocusManager.current
    var isOptionExpand: Boolean by rememberSaveable { mutableStateOf(false) }
    var selectedOption: String by rememberSaveable { mutableStateOf("") }

    val color =
        if (isInputError) {
            Color.Red
        } else {
            MaterialTheme.colors.primary
        }

    options.forEach { option ->
        if (selectedOptionId == option.id) {
            selectedOption = option.option
        }
    }


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed){
        if (isPressed) {
            isOptionExpand = !isOptionExpand
        }
    }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
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
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 16.dp)
                                .size(24.dp),
                            onClick = {
                                isOptionExpand = !isOptionExpand
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isOptionExpand) R.drawable.ic_round_expand_less_24 else R.drawable.ic_round_expand_more_24),
                                contentDescription = "",
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = MaterialTheme.colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        disabledBorderColor = MaterialTheme.colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = MaterialTheme.colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = MaterialTheme.colors.primaryVariant,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = MaterialTheme.colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = MaterialTheme.colors.primaryVariant

                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    readOnly = true,
                    interactionSource = interactionSource,
                    textStyle = Typography.body1,
                    value = selectedOption,
                    onValueChange = {},
                )

                DropdownMenu(
                    modifier = Modifier
                        .width(layoutWidth)
                        .heightIn(max = 200.dp),
                    expanded = isOptionExpand,
                    onDismissRequest = {isOptionExpand = false}
                ) {
                    options.forEach {option ->
                        DropdownMenuItem(
                            onClick = {
                                onOptionSelected(option.id)
                                isOptionExpand = false
                            }
                        ) {
                            Text(
                                text = option.option,
                                style = Typography.body1
                            )
                        }
                    }
                }

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
fun MenuOptionSelector(
    modifier: Modifier,
    label: String,
    selectedOptionId: Int,
    options: List<OptionItem>,
    isInputError: Boolean,
    onOptionSelected: (Int) -> Unit,
){
    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val focusManager = LocalFocusManager.current
    var isOptionExpand: Boolean by rememberSaveable { mutableStateOf(false) }
    var selectedOption: String by rememberSaveable { mutableStateOf("") }

    val color =
        if (isInputError) {
            Color.Red
        } else {
            MaterialTheme.colors.primary
        }

    options.forEach { option ->
        if (selectedOptionId == option.id) {
            selectedOption = option.option
        }
    }


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed){
        if (isPressed) {
            isOptionExpand = !isOptionExpand
        }
    }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
                .padding(vertical = 4.dp),
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
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
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 16.dp)
                                .size(24.dp),
                            onClick = {
                                isOptionExpand = !isOptionExpand
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isOptionExpand) R.drawable.ic_round_expand_less_24 else R.drawable.ic_round_expand_more_24),
                                contentDescription = "",
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = color,
                        disabledTextColor = MaterialTheme.colors.primaryVariant,
                        cursorColor = color,
                        unfocusedBorderColor = color,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        disabledBorderColor = MaterialTheme.colors.primaryVariant,
                        leadingIconColor = color,
                        disabledLeadingIconColor = MaterialTheme.colors.primaryVariant,
                        trailingIconColor = color,
                        disabledTrailingIconColor = MaterialTheme.colors.primaryVariant,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = color,
                        disabledLabelColor = MaterialTheme.colors.primaryVariant,
                        placeholderColor = color,
                        disabledPlaceholderColor = MaterialTheme.colors.primaryVariant

                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    readOnly = true,
                    interactionSource = interactionSource,
                    textStyle = Typography.body1,
                    value = selectedOption,
                    onValueChange = {},
                )

                DropdownMenu(
                    modifier = Modifier
                        .width(layoutWidth)
                        .heightIn(max = 200.dp),
                    expanded = isOptionExpand,
                    onDismissRequest = {isOptionExpand = false}
                ) {
                    options.forEach {option ->
                        DropdownMenuItem(
                            onClick = {
                                onOptionSelected(option.id)
                                isOptionExpand = false
                            }
                        ) {
                            Text(
                                text = option.option,
                                style = Typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomDropDownMenuPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        var selectedOptionId: Int by rememberSaveable { mutableStateOf(0) }
        var isOptionSelectorError: Boolean by rememberSaveable { mutableStateOf(false) }
        var optionSelectorErrorMessage: String by rememberSaveable { mutableStateOf("") }
        val optionSelectorFocusRequester = FocusRequester()
        SimpleOptionSelector(
            modifier = Modifier,
            icon = painterResource(id = R.drawable.ic_round_person_24),
            label = "Test",
            selectedOptionId = selectedOptionId,
            options = listOf(
                OptionItem(id = 1, option = "Test 1"),
                OptionItem(id = 2, option = "Test 2"),
                OptionItem(id = 3, option = "Test 3")
            ),
            isInputError = false,
            errorMessage = "",
            focusRequester = optionSelectorFocusRequester,
            onOptionSelected = {},
        )
    }
}