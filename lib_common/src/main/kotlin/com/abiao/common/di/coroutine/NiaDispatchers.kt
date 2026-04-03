package com.abiao.common.di.coroutine

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatch(val niaDispatcher: NiaDispatchers)

enum class NiaDispatchers {
    Default,
    IO
}