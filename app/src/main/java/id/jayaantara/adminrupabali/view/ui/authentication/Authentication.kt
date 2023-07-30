package id.jayaantara.adminrupabali.view.ui.authentication

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.view.ui.data.static.AddDataOnBoarding
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.common.Validation
import id.jayaantara.adminrupabali.common.fileManager
import id.jayaantara.adminrupabali.common.toMainActivity
import id.jayaantara.adminrupabali.view.components.*
import id.jayaantara.adminrupabali.view.ui.authentication.Form.FORM_LOGIN
import id.jayaantara.adminrupabali.view.ui.authentication.Form.FORM_REGISTER
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.io.File

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background
            )
    ) {

        // References
        val (
            overviewPager,
            textOverlay,
            gradientOverlay,
            form,
        ) = createRefs()

        val items = AddDataOnBoarding()
        val pagerState = rememberPagerState(
            initialPage = 0
        )

        LaunchedEffect(Unit){
            while (true){
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = tween(600)
                )
            }
        }

        // Pager On Boarding
        HorizontalPager(
            modifier = Modifier
                .constrainAs(overviewPager) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    height = Dimension.fillToConstraints
                }
                .width(screenWidth.times(0.6f)),
            state = pagerState,
            count = items.size
        ) { page ->
            Image(
                painter = painterResource(
                    id = items[page].image
                ),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop

            )
        }

        //Gradient Overlay
        Box(
            modifier = Modifier
                .constrainAs(gradientOverlay) {
                    top.linkTo(overviewPager.top)
                    bottom.linkTo(overviewPager.bottom)
                    start.linkTo(overviewPager.start)
                    end.linkTo(overviewPager.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(
                    brush = Brush.horizontalGradient(
                        0.75f to Color.Transparent,
                        0.95f to MaterialTheme.colors.background,
                        1.0f to MaterialTheme.colors.background,
                    )
                )
        )

        //Form
        var selectedForm: String by rememberSaveable { mutableStateOf(FORM_LOGIN) }


        when(selectedForm){
            FORM_LOGIN -> {
                LoginForm(
                    modifier = Modifier
                        .constrainAs(form) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(overviewPager.end)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .padding(horizontal = 64.dp),
                    navController = navController,
                    onRegisterClick = { selectedForm = FORM_REGISTER }
                )
            }

            FORM_REGISTER -> {
                RegisterForm(
                    modifier = Modifier
                        .constrainAs(form) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(overviewPager.end)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .padding(horizontal = 64.dp),
                    onBackClick = { selectedForm = FORM_LOGIN }
                )

            }
        }
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier,
    navController: NavController,
    onRegisterClick: (() -> Unit),
    viewModel: AuthenticationViewModel = hiltViewModel()
){

    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }
    var isError: Boolean by rememberSaveable { mutableStateOf(false) }


    //Context
    val context = LocalContext.current

    // Email TextField State
    val emailTextFieldLabel = stringResource(id = R.string.label_email)
    var emailTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isEmailTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var emailTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val emailTextFieldFocusRequester = FocusRequester()

    // Password TextField State
    val passwordTextFieldLabel = stringResource(id = R.string.label_password)
    var passwordTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isPasswordTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var passwordTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val passwordTextFieldFocusRequester = FocusRequester()

    //AlertDialog State
    var infoDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var infoDialogTitle: String by rememberSaveable { mutableStateOf("") }
    var infoDialogDescription: String by rememberSaveable { mutableStateOf("") }
    if (isError){
        LaunchedEffect(Unit){
            infoDialogState = true
        }
    }

    //AuthState
    viewModel.adminAuthState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    isError = false
                }
                is Resource.Success -> {
                    isLoading = false
                    isError = false
                    toMainActivity(navController)
                }
                is Resource.Error -> {
                    isLoading = false
                    isError = true
                    when(resource.errorCode) {
                        404 -> {
                            // Admin not found
                            infoDialogTitle = context.getString(R.string.label_alert_title_login_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_wrong_email_password)
                        }
                        401 -> {
                            // Admin not validate
                            infoDialogTitle = context.getString(R.string.label_alert_title_login_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_admin_not_validate)

                        }
                        400 -> {
                            // Error Input
                            infoDialogTitle = context.getString(R.string.label_alert_title_input_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                        }
                        else -> {
                            // Not Define Error
                            infoDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                        }
                    }
                }
            }
        }
    }

    Box(modifier = modifier
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Login Title Text
            Text(
                style = Typography.h1,
                maxLines = 1,
                text = stringResource(id = R.string.login_title)
            )

            // Login Description Text
            Text(
                style = Typography.body1,
                maxLines = 1,
                text = stringResource(id = R.string.login_description)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Text Field
            EmailTextField(
                modifier = Modifier,
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

            // Password Text Field
            PasswordTextField(
                modifier = Modifier,
                label = passwordTextFieldLabel,
                text = passwordTextFieldValue,
                isInputError = isPasswordTextFieldError,
                errorMessage = passwordTextFieldErrorMessage,
                focusRequester = passwordTextFieldFocusRequester,
                onInputChange = {
                    passwordTextFieldValue = it
                    if (Validation.isEmpty(it)) {
                        isPasswordTextFieldError = true
                        passwordTextFieldErrorMessage = context.getString(R.string.error_empty_password)
                    } else {
                        isPasswordTextFieldError = false
                    }

                }
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Forgot Password Text
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp),
                    style = Typography.body2,
                    maxLines = 1,
                    text = stringResource(id = R.string.lupa_password)
                )

                // Click Here Clickable Text
                ClickableText(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    style = Typography.overline,
                    maxLines = 1,
                    text = AnnotatedString(stringResource(id = R.string.click_here)),
                    onClick = {

                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
            ){
                // Login Button
                PositiveTextButtonFlex(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.label_login),
                    isVisible = true,
                    isEnable = true
                ){
                    if (Validation.isEmpty(emailTextFieldValue)) {
                        isEmailTextFieldError = true
                        emailTextFieldErrorMessage = context.getString(R.string.error_empty_email)
                        emailTextFieldFocusRequester.requestFocus()
                    } else if (Validation.isEmpty(passwordTextFieldValue)) {
                        isPasswordTextFieldError = true
                        passwordTextFieldErrorMessage = context.getString(R.string.error_empty_email)
                        passwordTextFieldFocusRequester.requestFocus()
                    } else {
                        viewModel.login(email = emailTextFieldValue, password = passwordTextFieldValue)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Register Button
                PositiveTextButtonFlex(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.label_register),
                    isVisible = true,
                    isEnable = true
                ){
                    onRegisterClick()
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
            // Watermark Text
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = Typography.caption,
                textAlign = TextAlign.Center,
                maxLines = 1,
                text = stringResource(id = R.string.app_name)
            )
        }

        LoadingScreen(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
            isLoading = isLoading,
            message = ""
        )

        InformationAlertDialog(
            title = infoDialogTitle,
            description = infoDialogDescription,
            dialogState = infoDialogState,
            onDismissRequest = {
                infoDialogState = !it
            },
        )
    }
}

@Composable
private fun RegisterForm(
    modifier: Modifier,
    onBackClick: (() -> Unit),
    viewModel: AuthenticationViewModel = hiltViewModel()
){

    //Context
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }
    var isError: Boolean by rememberSaveable { mutableStateOf(false) }


    // Email TextField State
    val emailTextFieldLabel = stringResource(id = R.string.label_email)
    var emailTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isEmailTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var emailTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val emailTextFieldFocusRequester = FocusRequester()


    // Fullname TextField State
    val fullnameTextFieldIcon = painterResource(id = R.drawable.ic_round_person_24)
    val fullnameTextFieldLabel = stringResource(id = R.string.label_fullname)
    var fullnameTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isFullnameTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var fullnameTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val fullnameTextFieldFocusRequester = FocusRequester()

    // Nickname TextField State
    val nicknameTextFieldIcon = painterResource(id = R.drawable.ic_round_person_24)
    val nicknameTextFieldLabel = stringResource(id = R.string.label_nickname)
    var nicknameTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isNicknameTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var nicknameTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val nicknameTextFieldFocusRequester = FocusRequester()

    // Password TextField State
    val passwordTextFieldLabel = stringResource(id = R.string.label_password)
    var passwordTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isPasswordTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var passwordTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val passwordTextFieldFocusRequester = FocusRequester()

    // Confirm Password TextField State
    val confirmPasswordTextFieldLabel = stringResource(id = R.string.label_confirm_password)
    var confirmPasswordTextFieldValue: String by rememberSaveable { mutableStateOf("") }
    var isConfirmPasswordTextFieldError: Boolean by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordTextFieldErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val confirmPasswordTextFieldFocusRequester = FocusRequester()

    // Admin Role
    var isAdminRoleSelectorError: Boolean by rememberSaveable { mutableStateOf(false) }
    var adminRoleSelectorErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val adminRoleSelectorFocusRequester = FocusRequester()
    var selectedRoleValue: String by rememberSaveable { mutableStateOf("") }
    var selectedRoleId: Int by rememberSaveable { mutableStateOf(0) }

    // Document Picker
    var documentPickerUri: String by rememberSaveable { mutableStateOf("") }
    var isDocumentPickerError: Boolean by rememberSaveable { mutableStateOf(false) }
    var documentPickerErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val documentPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { documentUri ->
        if (documentUri != null) {
            documentPickerUri = documentUri.toString()
        }
    }

    //IdentityCard Picker
    var identityCardPickerUri: String by rememberSaveable { mutableStateOf("") }
    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the cropped image
            identityCardPickerUri = result.uriContent.toString()
        } else {
            // an error occurred cropping
            val exception = result.error
        }
    }
    var isIdentityCardPickerError: Boolean by rememberSaveable { mutableStateOf(false) }
    var identityCardPickerErrorMessage: String by rememberSaveable { mutableStateOf("") }
    val identityCardPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null){
            val cropOptions = CropImageContractOptions(
                imageUri,
                CropImageOptions(
                    fixAspectRatio = true,
                    aspectRatioX = 3,
                    aspectRatioY = 2
                )
            )
            imageCropLauncher.launch(cropOptions)
        }
    }

    //Confirmation Dialog State
    var confirmationDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    val confirmationDialogTitle = stringResource(id = R.string.register_confirm_title)
    val confirmtionDialogDescription = stringResource(id = R.string.register_confirm_description)

    //Info Dialog State
    var infoDialogState: Boolean by rememberSaveable { mutableStateOf(false) }
    var infoDialogTitle: String by rememberSaveable { mutableStateOf("") }
    var infoDialogDescription: String by rememberSaveable { mutableStateOf("") }
    if (isError){
        LaunchedEffect(Unit){
            infoDialogState = true
        }
    }

    var onInfoDialogErrorCode: Int by rememberSaveable { mutableStateOf(0) }

    //Page state
    var pageNumber: Int by rememberSaveable { mutableStateOf(0) }

    //Scroll State
    val scrollState = rememberScrollState()

    //Check Email State
    viewModel.checkEmailState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isError = false
                }
                is Resource.Success -> {
                    isError = false
                    if (Validation.isValidEmail(emailTextFieldValue)) {
                        if (resource.data == true){
                            isEmailTextFieldError = true
                            emailTextFieldErrorMessage = context.getString(R.string.label_alert_message_email_already_used_problem)
                        } else {
                            isEmailTextFieldError = false
                        }
                    }
                }
                is Resource.Error -> {
                    isError = true
                    when(resource.errorCode) {
                        403 -> {
                            // Email Already Used
                            infoDialogTitle = context.getString(R.string.label_alert_title_register_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_email_already_used_problem)
                            onInfoDialogErrorCode = resource.errorCode
                        }
                        400 -> {
                            // Error Input
                            infoDialogTitle = context.getString(R.string.label_alert_title_input_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                        }
                        else -> {
                            // Not Define Error
                            infoDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                        }
                    }
                }
            }
        }
    }

    //RegisterState
    viewModel.registerState.observe(lifecycleOwner){ resource ->
        if (resource != null){
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    isError = false
                }
                is Resource.Success -> {
                    isLoading = false
                    isError = false
                    onBackClick()
                    viewModel.resetRegisterState()
                }
                is Resource.Error -> {
                    isLoading = false
                    isError = true
                    when(resource.errorCode) {
                        403 -> {
                            // Email Already Used
                            infoDialogTitle = context.getString(R.string.label_alert_title_register_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_email_already_used_problem)
                            onInfoDialogErrorCode = resource.errorCode
                        }
                        400 -> {
                            // Error Input
                            infoDialogTitle = context.getString(R.string.label_alert_title_input_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_input_problem)
                        }
                        else -> {
                            // Not Define Error
                            infoDialogTitle = context.getString(R.string.label_alert_title_undifine_problem)
                            infoDialogDescription = context.getString(R.string.label_alert_message_undifine_problem)
                        }
                    }
                }
            }
        }
    }

    Box(modifier = modifier
        .verticalScroll(state = scrollState)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 64.dp)
        ) {
            // Register Title Text
            Text(
                style = Typography.h1,
                maxLines = 1,
                text = stringResource(id = R.string.register_admin_title)
            )

            when(pageNumber){
                0 -> {
                    // Register Description Text
                    Text(
                        style = Typography.body1,
                        maxLines = 1,
                        text = stringResource(id = R.string.register_admin_description_data)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Fullname Text Field
                    ShortTextField(
                        modifier = Modifier,
                        icon = fullnameTextFieldIcon,
                        label = fullnameTextFieldLabel,
                        text = fullnameTextFieldValue,
                        isInputError = isFullnameTextFieldError,
                        errorMessage = fullnameTextFieldErrorMessage,
                        focusRequester = fullnameTextFieldFocusRequester,
                        onInputChange = {
                            fullnameTextFieldValue= it
                            if (Validation.isEmpty(fullnameTextFieldValue)) {
                                isFullnameTextFieldError = true
                                fullnameTextFieldErrorMessage = context.getString(R.string.error_empty_username)
                            } else {
                                isFullnameTextFieldError = false
                            }
                        }
                    )

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

                    // Email Text Field
                    EmailTextField(
                        modifier = Modifier,
                        label = emailTextFieldLabel,
                        text = emailTextFieldValue,
                        isInputError = isEmailTextFieldError,
                        errorMessage = emailTextFieldErrorMessage,
                        focusRequester = emailTextFieldFocusRequester,
                        onInputChange = {
                            emailTextFieldValue = it
                            if (!Validation.isValidEmail(emailTextFieldValue)) {
                                isEmailTextFieldError = true
                                emailTextFieldErrorMessage = context.getString(R.string.error_invalid_email)
                            } else {
                                viewModel.isEmailExist(email = emailTextFieldValue)
                            }

                        }
                    )

                    // Password Text Field
                    PasswordTextField(
                        modifier = Modifier,
                        label = passwordTextFieldLabel,
                        text = passwordTextFieldValue,
                        isInputError = isPasswordTextFieldError,
                        errorMessage = passwordTextFieldErrorMessage,
                        focusRequester = passwordTextFieldFocusRequester,
                        onInputChange = {
                            passwordTextFieldValue = it
                            if (!Validation.isValidPassword(passwordTextFieldValue)) {
                                isPasswordTextFieldError = true
                                passwordTextFieldErrorMessage = context.getString(R.string.error_invalid_password)
                            } else {
                                isPasswordTextFieldError = false
                            }

                        }
                    )


                    // Confirm Password Text Field
                    PasswordTextField(
                        modifier = Modifier,
                        label = confirmPasswordTextFieldLabel,
                        text = confirmPasswordTextFieldValue,
                        isInputError = isConfirmPasswordTextFieldError,
                        errorMessage = confirmPasswordTextFieldErrorMessage,
                        focusRequester = confirmPasswordTextFieldFocusRequester,
                        onInputChange = {
                            confirmPasswordTextFieldValue = it
                            if (Validation.isEmpty(confirmPasswordTextFieldValue)) {
                                isConfirmPasswordTextFieldError = true
                                confirmPasswordTextFieldErrorMessage = context.getString(R.string.error_empty_confirm_password)
                            } else {
                                isConfirmPasswordTextFieldError = false
                            }
                        }
                    )

                    // Role Selector
                    SimpleOptionSelector(
                        modifier = Modifier
                            .fillMaxWidth(),
                        icon = painterResource(id = R.drawable.ic_round_admin_panel_settings_24),
                        label = stringResource(id = R.string.label_role_admin),
                        selectedOptionId = selectedRoleId,
                        options = listOf(
                            OptionItem(id = 1, option = stringArrayResource(id = R.array.label_admin_role)[0]),
                            OptionItem(id = 2, option = stringArrayResource(id = R.array.label_admin_role)[1]),
                            OptionItem(id = 3, option = stringArrayResource(id = R.array.label_admin_role)[2]),
                        ),
                        isInputError = isAdminRoleSelectorError,
                        errorMessage = adminRoleSelectorErrorMessage,
                        focusRequester = adminRoleSelectorFocusRequester,
                        onOptionSelected = {
                            selectedRoleId = it
                            when(it){
                                1 -> {
                                    isAdminRoleSelectorError = false
                                    selectedRoleValue = AdminRole.SUPER_ADMIN.role
                                }
                                2 -> {
                                    isAdminRoleSelectorError = false
                                    selectedRoleValue = AdminRole.ADMIN.role
                                }
                                3 -> {
                                    isAdminRoleSelectorError = false
                                    selectedRoleValue = AdminRole.VALIDATOR.role
                                }
                                else -> {
                                    isAdminRoleSelectorError = true
                                    adminRoleSelectorErrorMessage = context.getString(R.string.error_empty_role)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        // Cancel Button
                        NegativeTextButtonFlex(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.label_cancel),
                            isVisible = true,
                            isEnable = true
                        ){
                            onBackClick()
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Register Button
                        PositiveTextButtonFlex(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.label_next),
                            isVisible = true,
                            isEnable = true
                        ){
                            if (Validation.isEmpty(fullnameTextFieldValue)) {
                                isFullnameTextFieldError = true
                                fullnameTextFieldErrorMessage = context.getString(R.string.error_empty_fullname)
                                fullnameTextFieldFocusRequester.requestFocus()
                            } else if (Validation.isEmpty(nicknameTextFieldValue)) {
                                isNicknameTextFieldError = true
                                nicknameTextFieldErrorMessage = context.getString(R.string.error_empty_nickname)
                                nicknameTextFieldFocusRequester.requestFocus()
                            }else if (Validation.isEmpty(emailTextFieldValue)) {
                                isEmailTextFieldError = true
                                emailTextFieldErrorMessage = context.getString(R.string.error_empty_email)
                                emailTextFieldFocusRequester.requestFocus()
                            } else if (Validation.isEmpty(passwordTextFieldValue)) {
                                isPasswordTextFieldError = true
                                passwordTextFieldErrorMessage = context.getString(R.string.error_empty_password)
                                passwordTextFieldFocusRequester.requestFocus()
                            } else if (Validation.isEmpty(confirmPasswordTextFieldValue)) {
                                isConfirmPasswordTextFieldError = true
                                confirmPasswordTextFieldErrorMessage = context.getString(R.string.error_empty_confirm_password)
                                confirmPasswordTextFieldFocusRequester.requestFocus()
                            } else if (!Validation.isConfirmPasswordSame(password= passwordTextFieldValue, confirmPassword= confirmPasswordTextFieldValue)) {
                                isConfirmPasswordTextFieldError = true
                                confirmPasswordTextFieldErrorMessage = context.getString(R.string.error_different_confirm_password)
                                confirmPasswordTextFieldFocusRequester.requestFocus()
                            }else if (Validation.isEmpty(selectedRoleValue)) {
                                isAdminRoleSelectorError = true
                                adminRoleSelectorErrorMessage = context.getString(R.string.error_empty_role)
                                adminRoleSelectorFocusRequester.requestFocus()
                            } else {
                                if (isFullnameTextFieldError) {
                                    fullnameTextFieldFocusRequester.requestFocus()
                                } else if (isNicknameTextFieldError) {
                                    nicknameTextFieldFocusRequester.requestFocus()
                                }else if (isEmailTextFieldError) {
                                    emailTextFieldFocusRequester.requestFocus()
                                } else if (isPasswordTextFieldError) {
                                    passwordTextFieldFocusRequester.requestFocus()
                                } else if (isConfirmPasswordTextFieldError) {
                                    confirmPasswordTextFieldFocusRequester.requestFocus()
                                }else if (isAdminRoleSelectorError) {
                                    adminRoleSelectorFocusRequester.requestFocus()
                                } else {
                                    pageNumber = 1
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Register Description Text
                    Text(
                        style = Typography.body1,
                        maxLines = 1,
                        text = stringResource(id = R.string.register_admin_description_document)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    //Identity Card
                    IdentityCardPicker(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = stringResource(id = R.string.label_input_admin_identity_card),
                        description = stringResource(id = R.string.description_input_admin_identity_card),
                        imageUrl = identityCardPickerUri,
                        isInputError = isIdentityCardPickerError,
                        errorMessage = identityCardPickerErrorMessage,
                    ) {
                        identityCardPickerLauncher.launch("image/*")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //Document Owner Picker
                    DocumentPicker(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = stringResource(id = R.string.label_input_document_verif_admin),
                        description = stringResource(id = R.string.description_input_document_verif_admin),
                        documentUrl = documentPickerUri,
                        isInputError = isDocumentPickerError,
                        errorMessage = documentPickerErrorMessage,
                        onClick = {
                            documentPickerLauncher.launch("application/pdf")
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        // Cancel Button
                        NegativeTextButtonFlex(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.label_previous),
                            isVisible = true,
                            isEnable = true
                        ){
                            pageNumber = 0
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Register Button
                        PositiveTextButtonFlex(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.label_register),
                            isVisible = true,
                            isEnable = true
                        ){
                            if (Validation.isEmpty(identityCardPickerUri)) {
                                isIdentityCardPickerError = true
                                identityCardPickerErrorMessage = context.getString(R.string.error_empty_identity_card)
                            } else if (Validation.isEmpty(documentPickerUri)) {
                                isDocumentPickerError = true
                                documentPickerErrorMessage = context.getString(R.string.error_empty_proving_document)
                            } else {
                                confirmationDialogState = true
                            }
                        }
                    }
                }
            }
        }

        ConfirmationAlertDialog(
            title = confirmationDialogTitle,
            description = confirmtionDialogDescription,
            dialogState = confirmationDialogState,
            onDismissRequest = {
                confirmationDialogState = !it
            },
            onConfirmButtonClick = {
                confirmationDialogState = false
                val identityCard = fileManager.uriToFileImage(
                    selectedImg = identityCardPickerUri.toUri(),
                    context = context
                )
                val provingDocument = fileManager.uriToFileDocument(
                    selectedDocument = documentPickerUri.toUri(),
                    context = context
                )

                viewModel.registerAdmin(
                    identityCard = identityCard,
                    provingDocument = provingDocument,
                    fullname = fullnameTextFieldValue,
                    nickname = nicknameTextFieldValue,
                    email = emailTextFieldValue,
                    password = passwordTextFieldValue,
                    role = selectedRoleValue
                )
            }
        )

        InformationAlertDialog(
            title = infoDialogTitle,
            description = infoDialogDescription,
            dialogState = infoDialogState,
            onDismissRequest = {
                infoDialogState = !it
                when(onInfoDialogErrorCode){
                    403 -> {
                        pageNumber = 0
                        isEmailTextFieldError = true
                        emailTextFieldErrorMessage = infoDialogDescription
                        emailTextFieldFocusRequester.requestFocus()
                    }
                }
            },
        )

        LoadingScreen(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
            isLoading = isLoading,
            message = ""
        )
    }

}

private object Form{
    const val FORM_LOGIN = "form_login"
    const val FORM_REGISTER = "form_regis"
}


@Preview(device = Devices.PIXEL_C)
@Composable
private fun AuthenticationScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        AuthenticationScreen(navController = rememberNavController())
    }
}