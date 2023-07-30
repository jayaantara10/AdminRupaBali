package id.jayaantara.adminrupabali.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.jayaantara.adminrupabali.view.theme.AdminRupaBaliTheme

@Composable
fun SubmissionArtworkProgressBar(
    modifier: Modifier,
    iconProgress: List<Painter>,
    pageState: Int,
){
    val progressIndicatorValue: Float by animateFloatAsState(
        when(pageState){
            0 -> 0.1f
            1 -> 0.2f
            2 -> 0.3f
            3 -> 0.4f
            4 -> 0.5f
            5 -> 0.6f
            6 -> 0.7f
            7 -> 0.8f
            8 -> 0.9f
            9 -> 1.0f
            10 -> 0.1f
            else -> 0.0f
        }
    )

    Box(modifier = modifier){
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
            ) {
                iconProgress.forEachIndexed(){ index,icon ->
                    Box(modifier = Modifier
                        .weight(1f)
                    ){
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                                    painter = icon,
                            tint = if (pageState >= index) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface,
                            contentDescription = ""
                        )
                    }

                }
            }

            Spacer(modifier = modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50))
                    .background(
                        brush = Brush.horizontalGradient(
                            progressIndicatorValue to MaterialTheme.colors.secondary,
                            progressIndicatorValue to MaterialTheme.colors.onSurface,
                            1.0f to MaterialTheme.colors.onSurface
                        ),
                    )
                    .fillMaxWidth()
                    .height(8.dp)

            )
        }
    }
}

@Composable
@Preview
private fun ProgressBarPreview(){
    AdminRupaBaliTheme(darkTheme = false) {
        SubmissionArtworkProgressBar(
            modifier = Modifier, 
            iconProgress = listOf(
                painterResource(id = 0)),
            pageState = 6
        )
    }
}