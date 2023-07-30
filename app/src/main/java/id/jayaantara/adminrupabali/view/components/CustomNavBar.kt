package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.view.Route
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme
import id.jayaantara.adminrupabali.view.theme.DarkBrown
import id.jayaantara.adminrupabali.view.theme.Gold200
import id.jayaantara.adminrupabali.view.theme.Typography
import id.jayaantara.adminrupabali.view.ui.main.MainRoute
import id.jayaantara.adminrupabali.view.ui.main.SideNavigationItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SideNavigation(
    modifier: Modifier,
    navController: NavController,
    navigationItems: List<SideNavigationItem>
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = navigationItems.any { it.route == currentDestination?.route}

    val spacerValue = 8.dp
    var isExpand: Boolean by rememberSaveable { mutableStateOf(false) }

    if(bottomBarDestination){
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(bottomEnd = 12.dp),
            backgroundColor = MaterialTheme.colors.primary,
            elevation = AppBarDefaults.BottomAppBarElevation
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(spacerValue))

                navigationItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any{
                        it.route == item.route
                    } == true

                    Row(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(item.route){
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                } }
                            .padding(horizontal = spacerValue.times(2), vertical = spacerValue),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = if (isSelected) item.iconSelected else item.iconUnselected),
                            contentDescription = item.description,
                            tint = if (isSelected) Gold200 else MaterialTheme.colors.background
                        )

                        AnimatedContent(
                            targetState = isExpand,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(150, 150)) with
                                        fadeOut(animationSpec = tween(150)) using
                                        SizeTransform { initialSize, targetSize ->
                                            if (targetState) {
                                                keyframes {
                                                    // Expand horizontally first.
                                                    IntSize(targetSize.width, initialSize.height) at 150
                                                    durationMillis = 300
                                                }
                                            } else {
                                                keyframes {
                                                    // Shrink vertically first.
                                                    IntSize(initialSize.width, targetSize.height) at 150
                                                    durationMillis = 300
                                                }
                                            }
                                        }
                            }
                        ) { targetExpanded ->
                            if (targetExpanded) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = spacerValue.times(2)),
                                    style = Typography.h3,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text = item.title,
                                    color = if (isSelected) Gold200 else MaterialTheme.colors.background
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(spacerValue))
            }

            Column(modifier = Modifier
                    .padding(start = spacerValue, top = spacerValue, bottom = spacerValue),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(spacerValue))

                IconButton(
                    onClick = {
                        isExpand = !isExpand
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = if (isExpand) R.drawable.ic_round_more_vert_24 else R.drawable.ic_round_menu_24),
                        contentDescription = "",
                        tint = MaterialTheme.colors.background
                    )
                }

                Spacer(modifier = Modifier.height(spacerValue))
            }
        }

    }
}

@Preview
@Composable
fun CustomNavigationPreview() {
    AdminRupaBaliTheme(darkTheme = false) {
        SideNavigation(
            modifier = Modifier
                .fillMaxHeight(),
            navController = rememberNavController(),
            navigationItems = listOf(
                SideNavigationItem(
                    title = "Test 1",
                    description = "",
                    iconSelected = R.drawable.ic_round_person_24,
                    iconUnselected = R.drawable.ic_round_person_24,
                    route = ""
                ),
                SideNavigationItem(
                    title = "Test 1",
                    description = "",
                    iconSelected = R.drawable.ic_round_person_24,
                    iconUnselected = R.drawable.ic_round_person_24,
                    route = ""
                ),
                SideNavigationItem(
                    title = "Test 1",
                    description = "",
                    iconSelected = R.drawable.ic_round_person_24,
                    iconUnselected = R.drawable.ic_round_person_24,
                    route = ""
                ),
                SideNavigationItem(
                    title = "Test 1",
                    description = "",
                    iconSelected = R.drawable.ic_round_person_24,
                    iconUnselected = R.drawable.ic_round_person_24,
                    route = ""
                ),
                SideNavigationItem(
                    title = "Test 1",
                    description = "",
                    iconSelected = R.drawable.ic_round_person_24,
                    iconUnselected = R.drawable.ic_round_person_24,
                    route = ""
                )
            )
        )
    }
}