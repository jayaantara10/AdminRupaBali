package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.theme.*
import id.jayaantara.adminrupabali.view.ui.data.dummy.*
import id.jayaantara.adminrupabali.view.ui.data.static.MenuItem
import id.jayaantara.adminrupabali.view.ui.data.static.UserCategoryItem
import kotlinx.coroutines.NonDisposableHandle.parent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminCard(
    modifier: Modifier,
    item: AdminItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.profilePicture),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.name
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = item.email
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.5f)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.role
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.5f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.5f)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = if (item.isVerify) Color.Red else MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = stringResource(
                            id = if (item.isVerify) R.string.is_suspend else R.string.not_suspend
                        )
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(
    modifier: Modifier,
    item: UserItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.profilePicture),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.username
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = item.email
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.role
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = if (item.isSuspend) Color.Red else MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = stringResource(
                            id = if (item.isSuspend) R.string.is_suspend else R.string.not_suspend
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = if (item.isSuspend) Color.Red else MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = item.verify_status
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworkCard(
    modifier: Modifier,
    item: ArtworkItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = item.verificationNumber
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.title
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = item.owner
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.fine_art_category
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.artwork_category
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = if (item.isSuspend) Color.Red else MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = stringResource(
                            id = if (item.isSuspend) R.string.is_suspend else R.string.not_suspend
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = if (item.isSuspend) Color.Red else MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = item.verificationStatus
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FineArtCategoryCard(
    modifier: Modifier,
    item: FineArtCategoryItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.category
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = item.validationStatus
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworkCategoryCard(
    modifier: Modifier,
    item: ArtworkCategoryItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.category
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.fineArtCategory
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50))
                        .background(
                            color = MaterialTheme.colors.secondary
                        )
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = Typography.body2,
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = item.validationStatus
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsCard(
    modifier: Modifier,
    item: NewsItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.title
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = item.date
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.2f)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.source
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventCard(
    modifier: Modifier,
    item: EventItem,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.h3,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    text = item.title
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = item.organizer
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.start_date) + item.startDate
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.end_date) + item.endDate
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_createdAt) + item.createdAt
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = Typography.body2,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.label_updatedAt) + item.updatedAt
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuCardSmall(
    modifier: Modifier,
    item: MenuItem,
    isSelected: Boolean,
    onClick: (() -> Unit)
){

    val gradient: Float by animateFloatAsState(if (isSelected) 0.8f else 0f)
    val lineWidth: Dp by animateDpAsState(if (isSelected) 64.dp else 0.dp)

    Card(
        modifier = modifier
            .width(136.dp)
            .height(48.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 4.dp,
        onClick = onClick
    ){

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ){
            Image(
                painter = item.image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            gradient to Color.Black,
                            1.0f to Color.Black,
                        ),
                        alpha = 0.6f
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = item.menu,
                    style = Typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(64.dp)
                ){
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(lineWidth)
                            .background(Color.White)
                    )
                }

            }
        }
    }
}

@Preview(device = Devices.PIXEL_C)
@Composable
private fun CardPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        EventCard(modifier = Modifier, item = eventItems[0]) {

        }
    }
}