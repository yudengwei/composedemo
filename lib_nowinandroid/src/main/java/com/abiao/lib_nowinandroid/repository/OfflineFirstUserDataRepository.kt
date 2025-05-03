package com.abiao.lib_nowinandroid.repository

import com.abiao.lib_nowinandroid.data.model.UserData
import com.abiao.lib_nowinandroid.source.NiaPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
): UserDataRepository{

    override val userData: Flow<UserData> = niaPreferencesDataSource.userData

}