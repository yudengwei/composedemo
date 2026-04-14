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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.model.Contact
import com.abiao.lib_chatdemo.ui.B1
import com.abiao.util.R
import kotlinx.serialization.Serializable

@Serializable
object ContactPageKey: NavKey

@Composable
fun ContactPage(
    contacts: List<Contact>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .height(36.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.abiao.lib_chatdemo.R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(com.abiao.lib_chatdemo.R.string.search),
                style = B1.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
        ) {
            contacts.forEach { contact ->
                item(key = contact.id) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                    ) {
                        Image(
                            painter = painterResource(contact.drawableId),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}