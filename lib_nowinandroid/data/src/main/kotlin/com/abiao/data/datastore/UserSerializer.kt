package com.abiao.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.abiao.lib_nowinandroid.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserSerializer @Inject constructor(): Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences =
        try {
            UserPreferences.parseFrom(input)
        } catch (e: Exception) {
            throw CorruptionException("Cannot read proto.", e)
        }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}