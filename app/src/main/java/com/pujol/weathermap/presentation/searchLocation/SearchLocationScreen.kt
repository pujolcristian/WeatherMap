package com.pujol.weathermap.presentation.searchLocation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.pujol.weathermap.R
import com.pujol.weathermap.core.util.ShimmerLocationCoordinateItem
import com.pujol.weathermap.core.util.UiEvent
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.ui.componet.EmptyList
import com.pujol.weathermap.ui.componet.LocationCoordinateItem
import com.pujol.weathermap.ui.theme.AntiFlash
import com.pujol.weathermap.ui.theme.DavGray

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchLocationScreen(
    viewModel: SearchLocationViewModel = hiltViewModel(),
    onSearchLocation: (LocationCoordinate?) -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(context) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigatePop -> {
                    onSearchLocation(event.locationCoordinate)
                }
                else -> {}
            }
        }
        focusRequester.requestFocus()
    }


    DisposableEffect(state.textFieldValue) {
        focusRequester.requestFocus()
        onDispose {}
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (search, spacer, history) = createRefs()

        Card(elevation = 16.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color.White,
            modifier = Modifier
                .height(60.dp)
                .constrainAs(search) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (arrow_back, query) = createRefs()
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    tint = DavGray,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(arrow_back) {
                            start.linkTo(parent.start, margin = 16.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            viewModel.onEvent(
                                SearchLocationEvent.OnNavigateUp
                            )
                        }
                )

                TextField(
                    value = state.textFieldValue, onValueChange = {
                        viewModel.onEvent(SearchLocationEvent.OnSetQuery(it))
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                        .constrainAs(query) {
                            start.linkTo(arrow_back.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top, margin = 4.dp)
                            bottom.linkTo(parent.bottom, margin = 4.dp)
                            width = Dimension.fillToConstraints
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions {
                        focusRequester.requestFocus()
                        keyboardController?.hide()
                        viewModel.onEvent(SearchLocationEvent.OnSearchClick(context))
                    },
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "",
                            tint = DavGray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.onEvent(SearchLocationEvent.OnSearchClick(context))
                                }
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                    )

                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(8.dp)
                .background(AntiFlash)
                .constrainAs(spacer) {
                    top.linkTo(search.bottom)
                }
        )

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .constrainAs(history) {
                    top.linkTo(spacer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            val textEmpty: String
            val list: List<LocationCoordinate>
            if (state.showSearchHistory) {
                list = state.searchHistory
                textEmpty = context.getString(R.string.no_search_performed)
            } else {
                list = state.locationCoordinates
                textEmpty = context.getString(R.string.result_no_found)
            }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (state.showSearchHistory) stringResource(R.string.recent_searches) else
                            stringResource(R.string.search_results),
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        painter = painterResource(id = state.iconResource),
                        tint = DavGray,
                        contentDescription = ""
                    )
                }
            }
            if (state.isLoading == true) {
                items(8) {
                    ShimmerLocationCoordinateItem()
                }
            } else if (!state.isLoading!! && list.isEmpty()) {
                item {
                    EmptyList(modifier = Modifier, textEmpty = textEmpty)
                }
            } else {
                items(items = list, itemContent = {
                    LocationCoordinateItem(
                        modifier = Modifier,
                        iconResource = state.iconResource,
                        locationCoordinate = it
                    ) { location ->
                        if (location.longitude == 0.0 && location.latitude == 0.0) {
                            keyboardController?.show()
                            viewModel.onEvent(
                                SearchLocationEvent.OnSetQuery(
                                    TextFieldValue(
                                        text = location.name!!,
                                        TextRange(location.name.length)
                                    )
                                )
                            )
                        } else {
                            viewModel.onEvent(SearchLocationEvent.OnNavigateToMap(location))
                        }
                    }
                })
            }
        }
    }
}