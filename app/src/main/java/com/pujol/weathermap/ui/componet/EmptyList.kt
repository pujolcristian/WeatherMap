package com.pujol.weathermap.ui.componet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pujol.weathermap.R

@Composable
fun EmptyList(modifier: Modifier, textEmpty: String) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder_emty_list),
            contentDescription = "",
            modifier = Modifier.size(150.dp),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(text = textEmpty)
    }
}