package id.jayaantara.adminrupabali.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography

@Composable
fun SearchBar(
    modifier: Modifier,
    hint : String,
    text : String,
    onInputChange : (String) -> Unit,
    onCancelClick : () -> Unit
){

    val color = colors.primary

    Box(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = colors.background,
                    shape = RoundedCornerShape(12.dp)
                )
        ){

            val (
                leadingIcon,
                textField,
                trailingIcon
            ) = createRefs()

            Icon(
                modifier = Modifier
                    .constrainAs(leadingIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_round_search_24),
                tint = color,
                contentDescription = "",
            )

            BasicTextField(
                modifier = Modifier
                    .constrainAs(textField) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(leadingIcon.end)
                        end.linkTo(trailingIcon.start)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(horizontal = 8.dp),
                value = text,
                onValueChange = onInputChange,
                decorationBox = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (text.isEmpty()){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1.copy(
                                    color = color
                                ),
                                maxLines = 1,
                                text = hint
                            )
                        }
                    }
                    it()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                cursorBrush = Brush.verticalGradient(
                    0.00f to color,
                    0.35f to color,
                    0.35f to color,
                    0.90f to color,
                    0.90f to color,
                    1.00f to color
                ),
                singleLine = true,
                textStyle = Typography.body1.copy(
                    color = color,
                )
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(trailingIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 16.dp)
                    .size(24.dp),
                onClick = onCancelClick
            ) {
                if (text.isNotEmpty()){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_cancel_24),
                        tint = color,
                        contentDescription = "",
                    )
                }

            }
        }
    }
}

@Composable
fun OutlinedSearchBar(
    modifier: Modifier,
    hint : String,
    text : String,
    onInputChange : (String) -> Unit,
    onCancelClick : () -> Unit
){

    val color = colors.primary

    Box(
        modifier = modifier
            .border(width = 2.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(12.dp))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ){

            val (
                leadingIcon,
                textField,
                trailingIcon
            ) = createRefs()

            Icon(
                modifier = Modifier
                    .constrainAs(leadingIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_round_search_24),
                tint = color,
                contentDescription = "",
            )

            BasicTextField(
                modifier = Modifier
                    .constrainAs(textField) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(leadingIcon.end)
                        end.linkTo(trailingIcon.start)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(horizontal = 8.dp),
                value = text,
                onValueChange = onInputChange,
                decorationBox = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (text.isEmpty()){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically),
                                style = Typography.body1.copy(
                                    color = color
                                ),
                                maxLines = 1,
                                text = hint
                            )
                        }
                    }
                    it()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                cursorBrush = Brush.verticalGradient(
                    0.00f to color,
                    0.35f to color,
                    0.35f to color,
                    0.90f to color,
                    0.90f to color,
                    1.00f to color
                ),
                singleLine = true,
                textStyle = Typography.body1.copy(
                    color = color,
                )
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(trailingIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 16.dp)
                    .size(24.dp),
                onClick = onCancelClick
            ) {
                if (text.isNotEmpty()){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_cancel_24),
                        tint = color,
                        contentDescription = "",
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        SearchBar(
            modifier = Modifier,
            hint = stringResource(id = R.string.label_home_search_bar),
            text = "",
            onInputChange = {},
            onCancelClick = {}
        )
    }
}