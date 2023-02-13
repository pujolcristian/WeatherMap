package com.pujol.weathermap.core.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pujol.weathermap.ui.theme.AntiFlash

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun ShimmerLocationCoordinateItem(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        val (location_icon, tittle, helper, arrow_icon, divider) = createRefs()

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .shimmerEffect()
                .constrainAs(location_icon) {
                    top.linkTo(tittle.top)
                    bottom.linkTo(helper.bottom)
                    start.linkTo(parent.start, margin = 20.dp)
                }
        )

        Box(
            modifier = Modifier
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .shimmerEffect()
                .constrainAs(tittle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(location_icon.end, margin = 20.dp)
                    end.linkTo(arrow_icon.start, margin = 20.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Box(
            modifier = Modifier
                .height(10.dp)
                .clip(RoundedCornerShape(50))
                .shimmerEffect()
                .constrainAs(helper) {
                    top.linkTo(tittle.bottom, margin = 4.dp)
                    start.linkTo(tittle.start)
                    end.linkTo(tittle.end)
                    width = Dimension.fillToConstraints
                }
        )

        Box(
            modifier = Modifier
                .shimmerEffect()
                .clip(RoundedCornerShape(50))
                .size(16.dp)
                .constrainAs(arrow_icon) {
                    top.linkTo(tittle.top)
                    bottom.linkTo(helper.bottom)
                    end.linkTo(parent.end, margin = 20.dp)
                }
        )

        Divider(modifier = Modifier
            .height(0.8.dp)
            .shimmerEffect()
            .background(AntiFlash)
            .constrainAs(divider) {
                end.linkTo(parent.end)
                start.linkTo(tittle.start)
                top.linkTo(helper.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            })

    }

}