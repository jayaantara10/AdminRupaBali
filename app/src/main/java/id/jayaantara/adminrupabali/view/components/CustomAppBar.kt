package id.jayaantara.adminrupabali.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.common.contentLinkBuilder
import id.jayaantara.adminrupabali.domain.model.Admin
import id.jayaantara.adminrupabali.view.theme.*
import id.jayaantara.adminrupabali.view.ui.data.dummy.dummyProfile


@Composable
fun HomeTopAppBar(
    modifier: Modifier,
    adminData: Admin,
    onHomeClick: () -> Unit = {},
    onChangeProfilePictureClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {

    var isShowProfile: Boolean by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        backgroundColor = DarkBrown,
        elevation = AppBarDefaults.TopAppBarElevation
    ){
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
        ) {
            // References
            val (
                titleText,
                homeButton,
                profileButton,
                notificationButton,
                settingsButton
            ) = createRefs()

            // Home Button
            Box(
                modifier = Modifier
                    .constrainAs(homeButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                    }
                    .wrapContentWidth(),
            ) {
                IconButton(
                    onClick = onHomeClick,
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.rupa_bali_logo_03
                        ),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

            // App name
            Text(
                modifier = Modifier
                    .constrainAs(titleText) {
                        top.linkTo(homeButton.top)
                        bottom.linkTo(homeButton.bottom)
                        start.linkTo(homeButton.end)
                        end.linkTo(profileButton.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 16.dp, end = 16.dp),
                style = Typography.h2,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                text = stringResource(id = R.string.app_name)
            )

            //  Profile Menu
            Box(
                modifier = Modifier
                    .constrainAs(profileButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(notificationButton.start)
                        height = Dimension.fillToConstraints
                    }
                    .padding(end = 16.dp)
                    .width(24.dp),
            ) {
                IconButton(
                    onClick = { isShowProfile = !isShowProfile },
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_round_person_4_24
                        ),
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                MaterialTheme(
                    colors = MaterialTheme.colors.copy(surface = DarkBrown),
                    shapes = MaterialTheme.shapes.copy(
                        medium = RoundedCornerShape(
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                ) {
                    DropdownMenu(
                        offset = DpOffset(x = (-112).dp, y = 16.dp),
                        expanded = isShowProfile,
                        onDismissRequest = { isShowProfile = false }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(320.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            // Profile Picture
                            Image(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(shape = CircleShape)
                                    .clickable {
                                        onChangeProfilePictureClick()
                                    }
                                    .background(Color.Gray),
                                painter =
                                    if(adminData.profilePicture != ""){
                                        rememberAsyncImagePainter(
                                            model = contentLinkBuilder.adminProfileData(adminData.profilePicture),
                                            placeholder = painterResource(id = R.drawable.ic_round_person_24),
                                            error = painterResource(id = R.drawable.ic_round_broken_image_24)
                                        )
                                    } else { painterResource(id = R.drawable.ic_round_person_24) },
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Nickname Text
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                style = Typography.h2,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                text = adminData.nickname
                            )

                            // Role Text
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                style = Typography.body1,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = when(adminData.role){
                                    AdminRole.SUPER_ADMIN -> stringArrayResource(R.array.label_admin_role)[0]
                                    AdminRole.ADMIN -> stringArrayResource(R.array.label_admin_role)[1]
                                    AdminRole.VALIDATOR -> stringArrayResource(R.array.label_admin_role)[2]
                                    else -> stringArrayResource(R.array.label_admin_role)[3]
                                }
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            //Button Edit Profile
                            Button(
                                onClick = {
                                    onEditProfileClick()
                                },
                                enabled = true,
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Brown)

                            ) {
                                Text(
                                    style = Typography.button,
                                    color = Color.White,
                                    text = stringResource(id = R.string.label_edit_profile)
                                )

                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            //Button Change Password
                            Button(
                                onClick = {
                                    onChangePasswordClick()
                                },
                                enabled = true,
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Brown)

                            ) {
                                Text(
                                    style = Typography.button,
                                    color = Color.White,
                                    text = stringResource(id = R.string.label_change_password)
                                )

                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            //Button Hapus Akun
                            OutlinedButton(
                                onClick = {
                                    onDeleteAccountClick()
                                },
                                enabled = true,
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = Brown),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    style = Typography.button,
                                    color = Color.White,
                                    text = stringResource(id = R.string.label_hapus_akun))

                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            //Button Logout
                            OutlinedButton(
                                onClick = {
                                    onLogoutClick()
                                },
                                enabled = true,
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = Gold200),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    style = Typography.button,
                                    color = Gold200,
                                    text = stringResource(id = R.string.label_logout))

                            }
                        }

                    }
                }
            }

            //  Notification Menu
            Box(
                modifier = Modifier
                    .constrainAs(notificationButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(settingsButton.start)
                        height = Dimension.fillToConstraints
                    }
                    .padding(end = 16.dp)
                    .width(24.dp),
            ) {
                IconButton(
                    onClick = {  },
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_round_notifications_24
                        ),
                        contentDescription = "",
                        tint = MaterialTheme.colors.background
                    )
                }
            }

            //  Settings Menu
            Box(
                modifier = Modifier
                    .constrainAs(settingsButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                    .width(24.dp),
            ) {
                IconButton(
                    onClick = {  },
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_round_settings_24
                        ),
                        contentDescription = "",
                        tint = MaterialTheme.colors.background
                    )
                }
            }
        }
    }
}


@Preview(device = Devices.PIXEL_C)
@Composable
fun AppBarPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
//        HomeTopAppBar(
//            modifier = Modifier.fillMaxWidth(),
//            onHomeClick = {},
//        )
    }
}

