package com.pujol.weathermap.ui.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pujol.weathermap.R
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.ui.theme.AntiFlash
import com.pujol.weathermap.ui.theme.DavGray
import com.pujol.weathermap.ui.theme.Silver200

@Composable
fun LocationCoordinateItem(
    modifier: Modifier,
    iconResource: Int,
    locationCoordinate: LocationCoordinate,
    onClick: (LocationCoordinate) -> Unit
) {
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onClick(locationCoordinate)
        }
        .background(Color.White)) {
        val (location_icon, tittle, helper, arrow_icon, divider) = createRefs()

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(AntiFlash)
                .constrainAs(location_icon) {
                    top.linkTo(tittle.top)
                    bottom.linkTo(helper.bottom)
                    start.linkTo(parent.start, margin = 20.dp)
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = DavGray
            )
        }

        Text(
            text = locationCoordinate.name ?: "",
            fontSize = 16.sp,
            color = DavGray,
            modifier = Modifier
                .constrainAs(tittle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(location_icon.end, margin = 20.dp)
                    end.linkTo(arrow_icon.start)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = locationCoordinate.state ?: locationCoordinate.country ?: "",
            fontSize = 13.sp,
            color = Silver200,
            modifier = Modifier
                .constrainAs(helper) {
                    top.linkTo(tittle.bottom, margin = 4.dp)
                    start.linkTo(tittle.start)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(arrow_icon) {
                    top.linkTo(tittle.top)
                    bottom.linkTo(helper.bottom)
                    end.linkTo(parent.end, margin = 20.dp)
                },
            tint = Color.Black
        )

        Divider(modifier = Modifier
            .height(0.8.dp)
            .background(AntiFlash)
            .constrainAs(divider) {
                end.linkTo(parent.end)
                start.linkTo(tittle.start)
                top.linkTo(helper.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            })
    }

}