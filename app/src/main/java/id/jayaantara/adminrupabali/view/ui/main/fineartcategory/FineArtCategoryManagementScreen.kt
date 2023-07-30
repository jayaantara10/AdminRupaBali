package id.jayaantara.adminrupabali.view.ui.main.fineartcategory

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.components.*
import id.jayaantara.adminrupabali.view.dev.UnderDevelopmentScreen
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.view.ui.data.static.MenuItem
import id.jayaantara.adminrupabali.view.ui.main.artwork.ArtworkManagementScreen

@Composable
fun FineArtCategoryManagementScreen(
    navController: NavController,
    navbarWidth: Dp
){
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = navbarWidth)
        ) {
            //Title Text
            Text(
                style = Typography.h1,
                maxLines = 1,
                text = stringResource(id = R.string.management_fine_art_category_title)
            )

            //Description Text
            Text(
                style = Typography.body1,
                maxLines = 3,
                text = stringResource(id = R.string.management_fine_art_category_description)
            )

            // Management Menu
            MenuList(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                navController = navController
            )

            //List Data
            DataListView(
                modifier = Modifier
                    .fillMaxSize(),
                navController = navController
            )

        }
    }
}

@Composable
private fun DataListView(
    modifier: Modifier,
    navController: NavController
){

    // Search State
    val searchBarHint = stringResource(id = R.string.label_fine_art_search_bar)
    var searchBarValue: String by rememberSaveable { mutableStateOf("") }

    // Sorting Selector State
    var selectedSortingOptionId: Int by rememberSaveable { mutableStateOf(0) }

    //Dialog State
    var sortingDialogState: Boolean by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.onPrimary,
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Title List Text
                Text(
                    modifier = Modifier
                        .weight(1f),
                    style = Typography.h2,
                    maxLines = 1,
                    text = stringResource(id = R.string.list_fine_art_title)
                )

                Spacer(modifier = Modifier.width(16.dp))

                //Search Bar
                OutlinedSearchBar(
                    modifier = Modifier
                        .weight(1f),
                    hint = searchBarHint,
                    text = searchBarValue,
                    onInputChange = {
                        searchBarValue = it
                    }
                ) {
                    searchBarValue = ""
                }

                Spacer(modifier = Modifier.width(16.dp))

                PositiveIconButton(
                    modifier = Modifier
                        .size(56.dp),
                    icon = R.drawable.ic_round_sort_24
                ) {
                    sortingDialogState = !sortingDialogState
                }

            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
                color = MaterialTheme.colors.primary,
                thickness = 2.dp
            )
        }

    }

    CustomInputDialog(
        modifier = Modifier,
        title = stringResource(id = R.string.sorting_menu_alert_dialog_title),
        inputLayout = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MenuOptionSelector(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(id = R.string.sorting_selector_label),
                    selectedOptionId = selectedSortingOptionId,
                    options = listOf(
                        OptionItem(id = 1, option = "Name"),
                        OptionItem(id = 2, option = "Email"),
                        OptionItem(id = 2, option = "Role"),
                        OptionItem(id = 3, option = "Created"),
                        OptionItem(id = 4, option = "Updated"),
                    ),
                    isInputError = false,
                    onOptionSelected = {selectedSortingOptionId = it}
                )
            }
        },
        dialogState = sortingDialogState,
        dismissButtonLabel = stringResource(id = R.string.label_cancel),
        onDismissRequest = {
            sortingDialogState = !it
        },
        confirmationButtonLabel = stringResource(id = R.string.label_sort)
    ) {

    }
}

@Composable
private fun MenuList(
    modifier: Modifier,
    navController: NavController
){
    val menuItems = listOf(
        MenuItem (
            menu = stringResource(id = R.string.menu_all_data),
            image = painterResource(id = R.drawable.lukisan)
        ),
        MenuItem (
            menu = stringResource(id = R.string.menu_verification_data),
            image = painterResource(id = R.drawable.lukisan_2)
        )
    )

    var selectedMenu: String by rememberSaveable { mutableStateOf(menuItems[0].menu) }

    val context = LocalContext.current
    Box(
        modifier = modifier
    ){
        LazyRow{
            items( items = menuItems ){ menuItem ->
                MenuCardSmall(
                    modifier = Modifier
                        .padding(4.dp),
                    item = menuItem,
                    isSelected = selectedMenu.equals(menuItem.menu)
                ) {
                    selectedMenu = menuItem.menu
                    Log.d("Test", selectedMenu)
                }
            }
        }
    }
}


@Preview(device = Devices.PIXEL_C)
@Composable
private fun FineArtCategoryManagementScreenPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        FineArtCategoryManagementScreen(navController = rememberNavController(), navbarWidth = 48.dp)
    }
}