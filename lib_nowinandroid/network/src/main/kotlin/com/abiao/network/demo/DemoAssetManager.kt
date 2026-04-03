package com.abiao.network.demo

import java.io.InputStream

//是 Kotlin 里定义 函数式接口 的写法，也就是这个接口里只能有 一个抽象方法，这样它就可以像 lambda 一样来用
// 可以有多个非抽象方法
fun interface DemoAssetManager {
    fun open(fileName: String): InputStream
}