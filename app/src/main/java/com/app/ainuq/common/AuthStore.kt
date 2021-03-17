package com.app.ainuq.common

import android.content.Context
import androidx.core.content.edit
import com.app.ainuq.ui.profile.UserItemUiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AuthStore {

    var user: UserItemUiModel?

}

class SharedPreferencesAuthStore @Inject constructor(
    private val moshi: Moshi,
    @ApplicationContext private val applicationContext: Context
) : AuthStore {

    private val sharedPreferences = applicationContext.getSharedPreferences(
        KEY_SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )


    override var user: UserItemUiModel?
        get() {
            val json = sharedPreferences.getString(KEY_USER, null)
            val adapter = moshi.adapter(UserItemUiModel::class.java)
            json?.let {
                return adapter.fromJson(json)
            } ?: run {
                return null
            }
        }
        set(value) {
            val adapter: JsonAdapter<UserItemUiModel> = moshi.adapter(UserItemUiModel::class.java)
            sharedPreferences.edit {
                if (value == null) {
                    putString(KEY_USER, value)
                } else {
                    putString(KEY_USER, adapter.toJson(value))
                }
            }
        }


    companion object {
        const val KEY_SHARED_PREFERENCES_NAME = "keySharedPreferencesName"
        const val KEY_USER = "keyUser"
    }

}