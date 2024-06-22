package com.imagepick.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abiao.common.theme.black
import com.abiao.common.theme.blue

@Composable
fun MyScrollColumn(

) {
    val students = List(20) { index ->
        Student(name = "第${index}个")
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                // 控件就能滑动了
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            students.forEach {student ->
                Box(
                    modifier = Modifier
                        .background(color = com.abiao.common.theme.blue)
                ) {
                    Text(
                        text = student.name,
                        color = com.abiao.common.theme.black,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MyLazyColumn(

) {
    val students = List(20) { index ->
        Student(name = "第${index}个")
    }
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .background(color = Color.Red),
        contentPadding = PaddingValues(10.dp)
    ) {
//        stickyHeader {
//            Text(text = "我是Item")
//        }
//        item {
//            Text(text = students[0].name)
//        }
//        stickyHeader {
//            Text(text = "我是items(3)")
//        }
//        items(3) {index ->
//            Text(text = students[index].name)
//        }
//        stickyHeader {
//            Text(text = "我是items(students)")
//        }
//        items(students) {student ->
//            Text(text = student.name)
//        }
        items(2) {
            Box(modifier = Modifier
                .background(Color.Blue)
                .fillMaxWidth()
                .height(50.dp))
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

data class Student(val name : String)