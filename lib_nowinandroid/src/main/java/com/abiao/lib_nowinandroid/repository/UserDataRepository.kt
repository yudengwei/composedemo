package com.abiao.lib_nowinandroid.repository

import com.abiao.lib_nowinandroid.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>
}