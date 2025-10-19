package com.example.a10bit_android.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.a10bit_android.data.UserPrefsKeys.IS_CHECKED
import com.example.a10bit_android.data.UserPrefsKeys.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// DataStore 인스턴스 (파일 이름: user_prefs)
private val Context.dataStore by preferencesDataStore(name = "user_prefs")
private val PUBLIC_ID = stringPreferencesKey("public_id")

@Singleton
class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // PUBLIC_ID를 Flow로 : 비동기 데이터의 변화를 실시간으로 관찰할 수 있게 하기 위함
    val publicIdFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PUBLIC_ID]
        }

    // 저장 함수: 모든 사용자 데이터를 DataStore에 저장
    suspend fun saveUserData(
        publicId: String? = null,
        token: String? = null,
        isChecked: Boolean? = null
    ) {
        context.dataStore.edit { prefs ->
            publicId?.let { prefs[PUBLIC_ID] = it }
            token?.let { prefs[TOKEN] = it }
            isChecked?.let { prefs[IS_CHECKED] = it }
        }
    }

    // 초기화 함수: 저장된 모든 사용자 데이터를 제거
    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            // 원래 코드의 초기화 로직을 그대로 구현
            prefs.remove(PUBLIC_ID)
            prefs.remove(TOKEN)
            prefs.remove(IS_CHECKED)
        }
    }

    suspend fun getPublicIdSnapshot(): String? =
        context.dataStore.data.map { it[PUBLIC_ID] }.first()

    suspend fun getTokenSnapshot(): String? =
        context.dataStore.data.map { it[TOKEN] }.first()

    suspend fun getIsCheckedSnapshot(): Boolean =
        context.dataStore.data.map { it[IS_CHECKED] ?: false }.first()
}
