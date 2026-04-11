package com.abiao.lib_chatdemo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.B1
import com.abiao.lib_chatdemo.ui.B2
import com.abiao.lib_chatdemo.ui.H2
import com.abiao.lib_chatdemo.ui.SurfaceColorDark
import com.abiao.lib_chatdemo.weight.ChatButton
import com.abiao.lib_chatdemo.weight.ChatTopBar
import kotlinx.serialization.Serializable

@Serializable
object InputNumberPageKey : NavKey

@Composable
fun InputNumberPage(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            ChatTopBar(
                onBackClick = {
                    onBackClick()
                }
            )
        },
        modifier = Modifier
            .statusBarsPadding()
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Your Phone Number",
                style = H2.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.padding(top = 80.dp)
            )
            Text(
                text = "Please confirm your country code and enter your phone number",
                style = B2.copy(color = MaterialTheme.colorScheme.onSurface),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, top = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 46.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .height(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_flag),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+86",
                        style = B1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                PhoneTextField(
                    phoneNumber = phoneNumber,
                    onInputChange = {input ->
                        phoneNumber = input.filter(Char::isDigit).take(11)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            ChatButton(
                text = stringResource(R.string.continue_next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 24.dp, end = 24.dp),
                onClick = {
                    if (phoneNumber.isNotEmpty()) {
                        onContinueClick()
                    }
                },
                enable = phoneNumber.isNotEmpty()
            )
        }
    }
}

@Composable
fun PhoneTextField(
    phoneNumber: String,
    onInputChange: (String) -> Unit,
    modifier: Modifier= Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        value = phoneNumber,
        onValueChange = { input ->
            onInputChange(input)
        },
        textStyle = B1.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    if (phoneNumber.isEmpty()) {
                        Text(
                            text = "Phone Number",
                            style = B2.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                    }
                    innerTextField()
                }
            }
        },
        modifier = modifier
    )
}
