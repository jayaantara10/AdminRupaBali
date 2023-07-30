package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(modifier: Modifier, items: List<Any>, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ){
        repeat(items.size) {
            Indicator(
                isSelected = it == currentPage
            )
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 16.dp else 8.dp)

    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(8.dp)
            .width(width.value),
        backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant ,
        shape = RoundedCornerShape(50)

    ){

    }
}