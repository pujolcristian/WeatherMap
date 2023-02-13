package com.pujol.weathermap.presentation.map.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pujol.weathermap.R
import com.pujol.weathermap.domain.model.LocationWeather
import com.pujol.weathermap.ui.theme.DarkBlue
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherScreen(
    locationWeather: LocationWeather = LocationWeather(),
    modifier: Modifier,
    timeStamp: String,
    isLoading: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF122259),
                        Color(0xFFFEFEFE)
                    )
                )
            )
    ) {
        val (city, time, weather, temp, detail) = createRefs()
        val verticalGuide = createGuidelineFromTop(0.40f)

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(city) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(verticalGuide)
                    }, color = Color.White, strokeWidth = 8.dp
            )
        } else {
            Text(
                text = locationWeather.name ?: "",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(city) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 22.dp)
                })

            Text(text = stringResource(R.string.acualized) + SimpleDateFormat(
                "dd/MM/yyyy hh:mm a",
                Locale("es", "es_MX")
            ).format(
                Date(locationWeather.dt?.times(1000) ?: 0L)
            ), color = Color.White, modifier = Modifier.constrainAs(time) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(city.bottom, margin = 4.dp)
            })

            Text(
                text = "${
                    locationWeather.weather?.firstOrNull()?.description?.capitalize(
                        Locale(
                            "es",
                            "es_MX"
                        )
                    )
                }", color = Color.White, modifier = Modifier.constrainAs(weather) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(time.bottom, margin = 32.dp)
                })
            Text(
                text = "${locationWeather.main?.temp?.toInt()}°C",
                fontSize = 62.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(temp) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(weather.bottom, margin = 22.dp)
                })
        }


        LazyVerticalGrid(
            modifier = Modifier.constrainAs(detail) {
                top.linkTo(verticalGuide)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
            },
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.sunrise,
                        tittle = stringResource(R.string.sunrice),
                        time = SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                        ).format(Date((locationWeather.sys?.sunrise?.times(1000)) ?: 0L))

                    )
                }
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.sunset,
                        tittle = stringResource(R.string.suntset),
                        time = SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                        ).format(Date(locationWeather.sys?.sunset?.times(1000) ?: 0L))

                    )
                }
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.ic_timer,
                        tittle = stringResource(R.string.hour),
                        time = timeStamp.removeRange(0..10)
                    )
                }
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.wind,
                        tittle = stringResource(R.string.wind),
                        time = "${locationWeather.wind?.speed}"
                    )
                }
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.presure,
                        tittle = stringResource(R.string.presure),
                        time = "${locationWeather.main?.pressure}"
                    )
                }
                item {
                    WeatherItem(
                        modifier = Modifier,
                        image = R.drawable.humidity,
                        tittle = stringResource(R.string.humidity),
                        time = "${locationWeather.main?.humidity}"
                    )
                }
            })

    }
}

@Composable
fun WeatherItem(modifier: Modifier, image: Int, tittle: String, time: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = (DarkBlue.copy(alpha = 0.16f)))
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image), contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(32.dp)
        )
        Text(text = tittle)
        Text(text = time)
    }
}