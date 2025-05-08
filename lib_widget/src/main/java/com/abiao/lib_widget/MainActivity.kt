package com.abiao.lib_widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.lib_widget.badge.MyBadge
import com.abiao.lib_widget.badge.MyNumBadge
import com.abiao.lib_widget.card.MyCard
import com.abiao.lib_widget.card.MyECard
import com.abiao.lib_widget.card.MyOCard
import com.abiao.lib_widget.check.CheckAll
import com.abiao.lib_widget.check.MyCheckBox
import com.abiao.lib_widget.datapick.MyDataPick
import com.abiao.lib_widget.dialog.MyAlertDialog
import com.abiao.lib_widget.divider.MyHorizontalDivider
import com.abiao.lib_widget.float.MyFloatView
import com.abiao.lib_widget.list.MyLazyColumn
import com.abiao.lib_widget.list.MyLazyGrid
import com.abiao.lib_widget.modal.MyModalBottomSheet
import com.abiao.lib_widget.text.TextColumn
import com.abiao.lib_widget.topbar.CenterAlignedTopAppBarExample
import com.abiao.lib_widget.topbar.LargeTitleExample
import com.abiao.lib_widget.topbar.MenuTitleExample
import com.abiao.lib_widget.topbar.TopAppBarEg

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                TextColumn()
            }
        }
    }
}