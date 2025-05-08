package com.abiao.lib_widget.badge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyBadge(
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
            Badge()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = ""
        )
    }
}

@Composable
fun MyNumBadge() {
    var itemCount by remember { mutableStateOf(0) }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text("$itemCount")
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = ""
            )
        }
        Button(
            onClick = {
                if (itemCount > 10) {
                    itemCount = 0
                } else {
                    itemCount += 1
                }
            }
        ) {
            Text("click add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBadgageDemo() {
    MyBadge()
}