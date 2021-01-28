package com.app.ainuq.common

import com.squareup.moshi.Moshi
import javax.inject.Inject

interface AuthStore {

}

class SharedPreferencesAuthStore @Inject constructor(moshi: Moshi) : AuthStore {

}