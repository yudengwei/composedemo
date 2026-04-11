package com.abiao.lib_chatdemo.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.B2
import com.abiao.lib_chatdemo.ui.H1
import com.abiao.lib_chatdemo.ui.H2
import com.abiao.lib_chatdemo.ui.S2
import com.abiao.lib_chatdemo.weight.ChatTopBar
import kotlinx.serialization.Serializable

@Serializable
object EnterCodePageKey: NavKey

@Composable
fun EnterCodePage(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit
) {
    Scaffold(
        topBar = {
            ChatTopBar(
                onBackClick = onBackClick
            )
        },
        modifier = Modifier
            .statusBarsPadding()
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Code",
                style = H2.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .padding(80.dp)
            )
            Text(
                text = "We have sent you an SMS with the code to +62 1309 - 1710 - 1920",
                style = B2.copy(color = MaterialTheme.colorScheme.onSurface),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, start = 56.dp, end = 56.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            var enterCodeString by remember { mutableStateOf("") }
            val count = 4
            EnterCodeInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 60.dp),
                value = enterCodeString,
                onValueChange = {input ->
                    enterCodeString = input.filter(Char::isDigit).take(count)
                    if (enterCodeString.length == count) {
                        onSuccess()
                    }
                },
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Resend Code",
                style = S2.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable {
                        enterCodeString = ""
                    }
            )
        }
    }
}

@Composable
private fun EnterCodeInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    count: Int = 4,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(Color.Transparent),
        modifier = modifier
            .focusRequester(focusRequester)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusRequester.requestFocus()
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(count) {index ->
                    CodeSlot(value.getOrNull(index))
                }
            }
        }
    )
}

@Composable
private fun CodeSlot(
    code: Char?,
) {
    Box(modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        if (code == null) {
            Box(modifier = Modifier.size(22.dp).background(color = colorResource(R.color.enter_code_bg_color), shape = CircleShape))
        } else {
            Text(text = code.toString(), style = H2.copy(color = MaterialTheme.colorScheme.onSurface))
        }
    }
}
