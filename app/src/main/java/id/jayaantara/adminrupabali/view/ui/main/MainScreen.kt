package id.jayaantara.adminrupabali.view.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.*
import id.jayaantara.adminrupabali.common.permissionUtils.checkAndRequestPermission
import id.jayaantara.adminrupabali.domain.model.Admin
import id.jayaantara.adminrupabali.view.components.ConfirmationAlertDialog
import id.jayaantara.adminrupabali.view.components.CustomInputDialog
import id.jayaantara.adminrupabali.view.components.HomeTopAppBar
import id.jayaantara.adminrupabali.view.components.InformationAlertDialog
import id.jayaantara.adminrupabali.view.components.ShortTextField
import id.jayaantara.adminrupabali.view.components.SideNavigation
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel()
){

    //LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    //Context
    val context = LocalContext.current

    //Screen State
    var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }
    var isSessionError: Boolean by rememberSaveable { mutableStateOf(false) }
    var isError: Boolean by rememberSaveable { mutableStateOf(false) }

    //Profile Picker
    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the cropped image
            val profilePicture = result.uriContent?.let {
                fileManager.uriToFileImage(
                    selectedImg = it,
                    context = context
                )
            }
            profilePicture?.let { viewModel.updateProfilePictureAdmin(profilePicture = it) }
        } else {
            // an error occurred cropping
            val exception = result.error
        }
    }
    val profilePicturePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null){
            val cropOptions = CropImageContractOptions(
                imageUri,
                CropImageOptions(
                    fixAspectRatio = true,
                    aspectRatioX = 1,
                    aspectRatioY = 1
                )
            )
            imageCropLauncher.launch(cropOptions)
        }
    }

    //External storage permission state
    val externalStoragePermission = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    LaunchedEffect(externalStoragePermission.hasPermission){
        if (externalStoragePermission.hasPermission){
            profilePicturePickerLauncher
        }
    }
//    PermissionRequired(
//        permissionState = externalStoragePermission,
//        permissionNotGrantedContent = {
//            var state: Boolean by rememberSaveable { mutableStateOf(false) }
//            InformationAlertDialog(
//                title = "Permission Denied",
//                description = "Your Permission is Denied",
//                dialogState = state,
//                onDismissRequest = {
//                    state = false
//                },
//            )
//        },
//        permissionNotAvailableContent = {
//            var state: Boolean by rememberSaveable { mutableStateOf(false) }
//            InformationAlertDialog(
//                title = "Permission Not Available",
//                description = "Your Permission is not available",
//                dialogState = state,
//                onDismissRequest = {
//                    state = false
//                },
//            )
//        }
//    ) {
//        profilePicturePickerLauncher.launch("image/*")
//    }

    //Session Alert Dialog State
    var sessionDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var sessionDialogTitle: String by rememberSaveable { mutableStateOf("") }
    var sessionDialogDescription: String by rememberSaveable { mutableStateOf("") }
    if (isSessionError){
        LaunchedEffect(Unit){
            sessionDialogState = true
        }
    }

    //Error Alert Dialog State
    var errorDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var errorDialogTitle: String by rememberSaveable { mutableStateOf("") }
    var errorDialogDescription: String by rememberSaveable { mutableStateOf("") }
    if (isError){
        LaunchedEffect(Unit){
            errorDialogState = true
        }
    }

    //Logout Dialog State
    var logoutDialogState: Boolean by rememberSaveable { mutableStateOf(false) }

    //Edit Profile Picture Dialog State
    var editProfilePictureDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var confirmEditProfilePictureDialogState: Boolean by rememberSaveable { mutableStateOf(false) }

    //Edit Profile Dialog State
    var editProfileDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var confirmEditProfileDialogState: Boolean by rememberSaveable { mutableStateOf(false) }

    // Admin Profile
    var admin by remember{ mutableStateOf<Admin?>(null) }

    // Nickname TextField State
    val nicknameTextFieldIcon = painterResource(id = R.drawable.ic_round_person_24)
    val nicknameTextFieldLabel = stringResource(id = R.string.label_nickname)
    var nicknameTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isNicknameTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var nicknameTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val nicknameTextFieldFocusRequester = FocusRequester()

    //Get Local Auth state
    viewModel.adminState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    isSessionError = false
                }
                is Resource.Success -> {
                    isLoading = false
                    isSessionError = false
                    if (resource.data != null){
                        resource.data.apply {
                            admin = resource.data
                            nicknameTextFieldValue = nickname
                        }
                    }
                }
                is Resource.Error -> {
                    isLoading = false
                    isSessionError = true
                    when(resource.errorCode) {
                        404 -> {
                            // Admin not found
                            sessionDialogTitle = context.getString(R.string.label_alert_title_auth_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_admin_not_found_problem)
                        }
                        401 -> {
                            // Session End
                            sessionDialogTitle = context.getString(R.string.label_alert_title_auth_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_auth_problem)
                        }
                        400 -> {
                            // Error Input
                            sessionDialogTitle = context.getString(R.string.label_alert_title_input_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                        }
                        else -> {
                            // Not Define Error
                            sessionDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                        }
                    }
                }
            }
        }
    }

    //Update Profile Picture state
    viewModel.updateProfilePictureAdminState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    isError = false
                }
                is Resource.Success -> {
                    isLoading = false
                    isError = false
                    viewModel.resetUpdateProfilePictureState()
                    viewModel.getProfileAdmin()
                }
                is Resource.Error -> {
                    isLoading = false
                    when(resource.errorCode) {
                        417 -> {
                            // Update Failed
                            errorDialogTitle = context.getString(R.string.label_alert_title_update_profile_picture_admin_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_update_failed_problem)
                            isError = true
                        }
                        401 -> {
                            // Session End
                            sessionDialogTitle = context.getString(R.string.label_alert_title_auth_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_auth_problem)
                            isSessionError = true
                        }
                        400 -> {
                            // Error Input
                            errorDialogTitle = context.getString(R.string.label_alert_title_update_profile_picture_admin_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                            isError = true
                        }
                        else -> {
                            // Not Define Error
                            errorDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                            isError = true
                        }
                    }
                }
            }
        }
    }

    //Update Profile state
    viewModel.updateProfileAdminState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    isError = false
                }
                is Resource.Success -> {
                    isLoading = false
                    isError = false
                    editProfileDialogState = false
                    viewModel.resetUpdateProfileState()
                    viewModel.getProfileAdmin()
                }
                is Resource.Error -> {
                    isLoading = false
                    when(resource.errorCode) {
                        417 -> {
                            // Update Failed
                            errorDialogTitle = context.getString(R.string.label_alert_title_update_profile_admin_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_update_failed_problem)
                            isError = true
                        }
                        401 -> {
                            // Session End
                            sessionDialogTitle = context.getString(R.string.label_alert_title_auth_problem)
                            sessionDialogDescription = context.getString(R.string.label_alert_message_auth_problem)
                            isSessionError = true
                        }
                        400 -> {
                            // Error Input
                            errorDialogTitle = context.getString(R.string.label_alert_title_update_profile_admin_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                            isError = true
                        }
                        else -> {
                            // Not Define Error
                            errorDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            errorDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                            isError = true
                        }
                    }
                }
            }
        }
    }

    val localDensity = LocalDensity.current

    var navbarWidthDp by remember {
        mutableStateOf(0.dp)
    }

    var layoutWidthDp by remember {
        mutableStateOf(0.dp)
    }

    var layoutHeightDp by remember {
        mutableStateOf(0.dp)
    }

    Scaffold(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                layoutWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                layoutHeightDp = with(localDensity) { coordinates.size.height.toDp() }
            },
        topBar = {
            admin?.let { adminData ->
                HomeTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    adminData = adminData,
                    onHomeClick = {},
                    onChangeProfilePictureClick = { editProfilePictureDialogState = true },
                    onEditProfileClick = { editProfileDialogState = true },
                    onChangePasswordClick = {},
                    onDeleteAccountClick = {},
                    onLogoutClick = { logoutDialogState = true }
                )
            }
        },
        bottomBar = {
            SideNavigation(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 64.dp)
                    .onGloballyPositioned { coordinates ->
                        navbarWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                    },
                navController = navController,
                navigationItems = when (admin?.role) {
                    AdminRole.SUPER_ADMIN -> {
                        listOf(
                            SideNavigationItem(
                                title = stringResource(id = R.string.dashboard_title),
                                description = stringResource(id = R.string.dashboard_title),
                                iconSelected = R.drawable.ic_round_home_24,
                                iconUnselected = R.drawable.ic_outline_home_24,
                                route = MainRoute.DASHBOARD
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_admin_title),
                                description = stringResource(id = R.string.manage_admin_title),
                                iconSelected = R.drawable.ic_round_person_4_24,
                                iconUnselected = R.drawable.ic_outline_person_4_24,
                                route = MainRoute.ADMIN_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_user_title),
                                description = stringResource(id = R.string.manage_user_title),
                                iconSelected = R.drawable.ic_round_person_24,
                                iconUnselected = R.drawable.ic_outline_person_24,
                                route = MainRoute.USER_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_artwork_title),
                                description = stringResource(id = R.string.manage_artwork_title),
                                iconSelected = R.drawable.ic_round_image_24,
                                iconUnselected = R.drawable.ic_outline_image_24,
                                route = MainRoute.ARTWORK_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_fine_art_category_title),
                                description = stringResource(id = R.string.manage_fine_art_category_title),
                                iconSelected = R.drawable.ic_round_palette_24,
                                iconUnselected = R.drawable.ic_outline_palette_24,
                                route = MainRoute.FINE_ART_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_artwork_category_title),
                                description = stringResource(id = R.string.manage_artwork_category_title),
                                iconSelected = R.drawable.ic_round_category_24,
                                iconUnselected = R.drawable.ic_outline_category_24,
                                route = MainRoute.ARTWORK_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_news_title),
                                description = stringResource(id = R.string.manage_news_title),
                                iconSelected = R.drawable.ic_round_article_24,
                                iconUnselected = R.drawable.ic_outline_article_24,
                                route = MainRoute.NEWS_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_event_title),
                                description = stringResource(id = R.string.manage_event_title),
                                iconSelected = R.drawable.ic_round_event_note_24,
                                iconUnselected = R.drawable.ic_outline_event_note_24,
                                route = MainRoute.EVENT_MANAGEMENT
                            )
                        )
                    }
                    AdminRole.ADMIN -> {
                        listOf(
                            SideNavigationItem(
                                title = stringResource(id = R.string.dashboard_title),
                                description = stringResource(id = R.string.dashboard_title),
                                iconSelected = R.drawable.ic_round_home_24,
                                iconUnselected = R.drawable.ic_outline_home_24,
                                route = MainRoute.DASHBOARD
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_user_title),
                                description = stringResource(id = R.string.manage_user_title),
                                iconSelected = R.drawable.ic_round_person_24,
                                iconUnselected = R.drawable.ic_outline_person_24,
                                route = MainRoute.USER_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_fine_art_category_title),
                                description = stringResource(id = R.string.manage_fine_art_category_title),
                                iconSelected = R.drawable.ic_round_palette_24,
                                iconUnselected = R.drawable.ic_outline_palette_24,
                                route = MainRoute.FINE_ART_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_artwork_category_title),
                                description = stringResource(id = R.string.manage_artwork_category_title),
                                iconSelected = R.drawable.ic_round_category_24,
                                iconUnselected = R.drawable.ic_outline_category_24,
                                route = MainRoute.ARTWORK_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_news_title),
                                description = stringResource(id = R.string.manage_news_title),
                                iconSelected = R.drawable.ic_round_article_24,
                                iconUnselected = R.drawable.ic_outline_article_24,
                                route = MainRoute.NEWS_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_event_title),
                                description = stringResource(id = R.string.manage_event_title),
                                iconSelected = R.drawable.ic_round_event_note_24,
                                iconUnselected = R.drawable.ic_outline_event_note_24,
                                route = MainRoute.EVENT_MANAGEMENT
                            )
                        )
                    }
                    AdminRole.VALIDATOR -> {
                        listOf(
                            SideNavigationItem(
                                title = stringResource(id = R.string.dashboard_title),
                                description = stringResource(id = R.string.dashboard_title),
                                iconSelected = R.drawable.ic_round_home_24,
                                iconUnselected = R.drawable.ic_outline_home_24,
                                route = MainRoute.DASHBOARD
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_artwork_title),
                                description = stringResource(id = R.string.manage_artwork_title),
                                iconSelected = R.drawable.ic_round_image_24,
                                iconUnselected = R.drawable.ic_outline_image_24,
                                route = MainRoute.ARTWORK_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_fine_art_category_title),
                                description = stringResource(id = R.string.manage_fine_art_category_title),
                                iconSelected = R.drawable.ic_round_palette_24,
                                iconUnselected = R.drawable.ic_outline_palette_24,
                                route = MainRoute.FINE_ART_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_artwork_category_title),
                                description = stringResource(id = R.string.manage_artwork_category_title),
                                iconSelected = R.drawable.ic_round_category_24,
                                iconUnselected = R.drawable.ic_outline_category_24,
                                route = MainRoute.ARTWORK_CATEGORY_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_news_title),
                                description = stringResource(id = R.string.manage_news_title),
                                iconSelected = R.drawable.ic_round_article_24,
                                iconUnselected = R.drawable.ic_outline_article_24,
                                route = MainRoute.NEWS_MANAGEMENT
                            ),
                            SideNavigationItem(
                                title = stringResource(id = R.string.manage_event_title),
                                description = stringResource(id = R.string.manage_event_title),
                                iconSelected = R.drawable.ic_round_event_note_24,
                                iconUnselected = R.drawable.ic_outline_event_note_24,
                                route = MainRoute.EVENT_MANAGEMENT
                            )
                        )
                    }
                    else -> {
                        listOf(
                            SideNavigationItem(
                                title = stringResource(id = R.string.dashboard_title),
                                description = stringResource(id = R.string.dashboard_title),
                                iconSelected = R.drawable.ic_round_home_24,
                                iconUnselected = R.drawable.ic_outline_home_24,
                                route = MainRoute.DASHBOARD
                            ),
                        )
                    }
                }
            )
        }
    ){
        Box(modifier = Modifier.fillMaxSize()){
            MainNavGraph(navController = navController, navbarWidth = navbarWidthDp)
            if (isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            // Error Alert Dialog
            InformationAlertDialog(
                title = sessionDialogTitle,
                description = sessionDialogDescription,
                dialogState = sessionDialogState,
                onDismissRequest = {
                    sessionDialogState = false
                },
            )

            // Session Alert Dialog
            InformationAlertDialog(
                title = sessionDialogTitle,
                description = sessionDialogDescription,
                dialogState = sessionDialogState,
                onDismissRequest = {
                    sessionDialogState = false
                    logout(viewModel = viewModel, context = context)
                },
            )

            // Logout Confirm Dialog
            ConfirmationAlertDialog(
                title = stringResource(id = R.string.label_alert_title_logout),
                description = stringResource(id = R.string.label_alert_message_logout),
                dialogState = logoutDialogState,
                onDismissRequest = {
                    logoutDialogState = false
                },
                onConfirmButtonClick = {
                    logoutDialogState = false
                    logout(viewModel = viewModel,
                        context = context
                    )
                }
            )

            // Edit Profile Picture
            CustomInputDialog(
                modifier = Modifier
                    .width(layoutWidthDp.div(2f))
                    .heightIn(max = layoutHeightDp - 128.dp),
                title = stringResource(id = R.string.label_dialog_edit_profile_picture),
                inputLayout = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Profile Picture
                        Image(
                            modifier = Modifier
                                .size(400.dp)
                                .background(Color.Gray),
                            painter =
                            if(admin?.profilePicture != ""){
                                rememberAsyncImagePainter(
                                    model = admin?.let { adminData ->
                                        contentLinkBuilder.adminProfileData(
                                            adminData.profilePicture)
                                    },
                                    placeholder = painterResource(id = R.drawable.ic_round_person_24),
                                    error = painterResource(id = R.drawable.ic_round_broken_image_24)
                                )
                            } else { painterResource(id = R.drawable.ic_round_person_24) },
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }},
                dialogState = editProfilePictureDialogState,
                dismissButtonLabel = stringResource(id = R.string.label_cancel),
                onDismissRequest = { editProfilePictureDialogState = false },
                confirmationButtonLabel = stringResource(id = R.string.label_change_picture),
                onConfirmButtonClick = {
                    editProfilePictureDialogState = false
                    if(externalStoragePermission.hasPermission){
                        profilePicturePickerLauncher.launch("image/*")
                    }else{
                        externalStoragePermission.launchPermissionRequest()
                    }
                }
            )

            // Edit Profile
            CustomInputDialog(
                modifier = Modifier
                    .width(layoutWidthDp.div(2f))
                    .heightIn(max = layoutHeightDp - 128.dp),
                title = stringResource(id = R.string.label_dialog_edit_profile),
                inputLayout = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Nickname Text Field
                        ShortTextField(
                            modifier = Modifier,
                            icon = nicknameTextFieldIcon,
                            label = nicknameTextFieldLabel,
                            text = nicknameTextFieldValue,
                            isInputError = isNicknameTextFieldError,
                            errorMessage = nicknameTextFieldErrorMessage,
                            focusRequester = nicknameTextFieldFocusRequester,
                            onInputChange = {
                                nicknameTextFieldValue= it
                                if (Validation.isEmpty(nicknameTextFieldValue)) {
                                    isNicknameTextFieldError = true
                                    nicknameTextFieldErrorMessage = context.getString(R.string.error_empty_username)
                                } else {
                                    isNicknameTextFieldError = false
                                }
                            }
                        )
                    }},
                dialogState = editProfileDialogState,
                dismissButtonLabel = stringResource(id = R.string.label_cancel),
                onDismissRequest = {
                    nicknameTextFieldValue = admin?.nickname ?: ""
                    isNicknameTextFieldError = false
                    editProfileDialogState = false
                                   },
                confirmationButtonLabel = stringResource(id = R.string.label_save),
                onConfirmButtonClick = {
                    if (Validation.isEmpty(nicknameTextFieldValue)) {
                        isNicknameTextFieldError = true
                        nicknameTextFieldErrorMessage = context.getString(R.string.error_empty_nickname)
                        nicknameTextFieldFocusRequester.requestFocus()
                    } else {
                        if (isNicknameTextFieldError) {
                            nicknameTextFieldFocusRequester.requestFocus()
                        } else {
                            confirmEditProfileDialogState = true
                        }
                    }
                }
            )

            // Edit Profile Confirm Dialog
            ConfirmationAlertDialog(
                title = stringResource(id = R.string.label_alert_title_edit_profile_admin),
                description = stringResource(id = R.string.label_alert_message_edit_profile_admin),
                dialogState = confirmEditProfileDialogState,
                onDismissRequest = {
                    confirmEditProfileDialogState = false
                },
                onConfirmButtonClick = {
                    viewModel.updateProfileAdmin(nickname = nicknameTextFieldValue)
                    confirmEditProfileDialogState = false
                }
            )
        }

    }
}

private fun logout(viewModel: MainViewModel, context: Context){
    viewModel.logout()
    backToRoot(context)
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.PIXEL_C)
@Composable
private fun MainScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        MainScreen()
    }
}