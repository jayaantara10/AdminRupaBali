package id.jayaantara.adminrupabali.view.components

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.Typography
import java.util.*

@Composable
fun SimpleDatePicker(
    modifier: Modifier,
    label: String,
    selectedDate: String,
    isInputError: Boolean,
    errorMessage: String,
    focusRequester: FocusRequester,
    onDateChange : (String) -> Unit,
){

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var date: String by rememberSaveable { mutableStateOf("") }

    val year: Int
    val month: Int
    val day: Int

    Calendar.getInstance().let { calendar ->
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()
    }

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.Theme_RupaBali_Dialog,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date = "$dayOfMonth/${month+1}/$year"
            onDateChange(date)
        }, year, month, day
    )

    val color =
        if (isInputError) {
            Color.Red
        } else {
            MaterialTheme.colors.primary
        }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed){
        if (isPressed) {
            datePickerDialog.show()
        }
    }

    Box(
        modifier = modifier
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
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
                            painter = painterResource(id = R.drawable.ic_round_edit_calendar_24),
                            contentDescription = "",

                            )
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
                    value = selectedDate,
                    onValueChange = {},
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

