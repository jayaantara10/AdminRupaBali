package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagePicker(
    modifier: Modifier,
    icon: Painter,
    text: String,
    imageUrl: String,
    onClick: (() -> Unit)
){

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
                .height(layoutWidth)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    painter = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = text,
                    style = Typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircleImagePicker(
    modifier: Modifier,
    icon: Painter,
    text: String,
    imageUrl: String,
    onClick: (() -> Unit)
){

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(layoutWidth.div(2f).toPx()))
            },
        shape = CircleShape,
        elevation = 4.dp,
        onClick = onClick
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
                .height(layoutWidth)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    painter = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = text,
                    style = Typography.body2,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoPicker(
    modifier: Modifier,
    icon: Painter,
    text: String,
    imageUrl: String,
    onClick: (() -> Unit)
){

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
                .height(layoutWidth.div(2f))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    painter = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = text,
                    style = Typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocationPicker(
    modifier: Modifier,
    imageUrl: String,
    icon: Painter,
    text: String,
    onClick: (() -> Unit)
){

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                }
                .height(layoutWidth)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    painter = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = text,
                    style = Typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentPicker(
    modifier: Modifier,
    label: String,
    description: String,
    documentUrl: String,
    isInputError: Boolean,
    errorMessage: String,
    onClick: (() -> Unit)
){
    val color =
        if (isInputError) {
            Color.Red
        } else {
            MaterialTheme.colors.primary
        }

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = color

    Column(
        modifier = modifier
    ){
        //Label
        Text(
            text = label,
            style = Typography.h3,
            fontWeight = FontWeight.Bold,
            color = color
        )

        //Description
        Text(
            text = description,
            style = Typography.caption,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preview
        AnimatedVisibility(
            visible = documentUrl != "",
            enter = fadeIn(
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                animationSpec = tween(250, 0, LinearEasing)
            )
        ){
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
            ){
                Card(
                    modifier = Modifier
                        .drawBehind {
                            drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    onClick = onClick
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                            }
                            .height(layoutWidth)
                    ){
                        val pdfState = rememberVerticalPdfReaderState(
                            resource = ResourceType.Local(uri = documentUrl.toUri()),
                            isZoomEnable = true
                        )
                        VerticalPDFReader(
                            state = pdfState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Gray)
                        )
                    }
                }
            }
        }

        // Add button
        PositiveTextButtonFlex(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(
                id =
                if (documentUrl == "") R.string.hint_input_document
                else R.string.hint_change_document
                    ),
            isVisible = true,
            isEnable = true
        ){
            onClick()
        }

        //Error Message
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailArtworkPicker(
    modifier: Modifier,
    text: String,
    onClick: (() -> Unit)
){

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = Typography.button,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworkAchievementPicker(
    modifier: Modifier,
    text: String,
    onClick: (() -> Unit)
){

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = Typography.button,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworkOwnershipHistoryPicker(
    modifier: Modifier,
    text: String,
    onClick: (() -> Unit)
){

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = MaterialTheme.colors.primary
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = Typography.button,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdentityCardPicker(
    modifier: Modifier,
    label: String,
    description: String,
    imageUrl: String,
    isInputError: Boolean,
    errorMessage: String,
    onClick: (() -> Unit)
){
    val color =
        if (isInputError) {
            Color.Red
        } else {
            MaterialTheme.colors.primary
        }

    val localDensity = LocalDensity.current
    var layoutWidth by remember {
        mutableStateOf(0.dp)
    }

    val stroke = Stroke(width = 10f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    val dashedStrokeColor = color

    Column(
        modifier = modifier
    ){
        //Label
        Text(
            text = label,
            style = Typography.h3,
            fontWeight = FontWeight.Bold,
            color = color
        )

        //Description
        Text(
            text = description,
            style = Typography.caption,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preview
        AnimatedVisibility(
            visible = imageUrl != "",
            enter = fadeIn(
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                animationSpec = tween(250, 0, LinearEasing)
            )
        ){
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
            ){
                Card(
                    modifier = Modifier
                        .drawBehind {
                            drawRoundRect(color = dashedStrokeColor, style = stroke, cornerRadius = CornerRadius(12.dp.toPx()))
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    onClick = onClick
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                layoutWidth = with(localDensity) { coordinates.size.width.toDp() }
                            }
                            .height(layoutWidth.div(1.5f))
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Add button
        PositiveTextButtonFlex(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(
                id =
                if (imageUrl == "") R.string.hint_input_identity_card
                else R.string.hint_change_identity_card
            ),
            isVisible = true,
            isEnable = true
        ){
            onClick()
        }

        //Error Message
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


@Preview
@Composable
fun CustomImagePickerPreview(){
    AdminRupaBaliTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ){
            CircleImagePicker(
                modifier = Modifier,
                icon = painterResource(id = R.drawable.ic_round_person_24),
                text = "",
                imageUrl = "",
                onClick = {}
            )
        }
    }
}