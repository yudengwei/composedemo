package com.abiao.lib_chatdemo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.B1
import com.abiao.lib_chatdemo.weight.ChatButton
import com.abiao.lib_chatdemo.weight.ChatTopBar
import kotlinx.serialization.Serializable
import kotlinx.coroutines.delay

@Serializable
object LoginPageKey: NavKey

@Composable
fun LoginPage(
    onBack: () -> Unit
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable {  mutableStateOf("") }
    val saveButtonBringIntoViewRequester = remember { BringIntoViewRequester() }
    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)

    LaunchedEffect(imeBottom) {
        if (imeBottom > 0) {
            saveButtonBringIntoViewRequester.bringIntoView()
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                title = stringResource(R.string.login_page_title)
            ) {
                onBack()
            }
        },
        modifier = Modifier
            .statusBarsPadding()
    ) {paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(R.drawable.change_avatar),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(40.dp))
            UserInput(
                name = firstName,
                hintString = stringResource(R.string.first_name)
            ) {
                firstName = it
            }
            Spacer(modifier = Modifier.height(12.dp))
            UserInput(
                name = lastName,
                hintString = stringResource(R.string.last_name)
            ) {
                lastName = it
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .bringIntoViewRequester(saveButtonBringIntoViewRequester)
            ) {
                ChatButton(
                    text = stringResource(R.string.save),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    },
                    enable = firstName.isNotEmpty()
                )
            }
        }
    }
}

@Composable
private fun UserInput(
    name: String,
    hintString: String? = null,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = name,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 24.dp),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = B1.copy(MaterialTheme.colorScheme.onSurfaceVariant),
        decorationBox = {innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                if (name.isEmpty() && hintString?.isNotEmpty() == true) {
                    Text(
                        text = hintString,
                        style = B1.copy(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                    )
                }
                innerTextField()
            }
        }
    )
}
