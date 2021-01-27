package com.app.ainuq.common

import com.app.ainuq.BuildConfig
import javax.inject.Inject

interface AppConfig {
    val  baseUrl: String
}

class AppConfigImplementation @Inject constructor() : AppConfig {
    override val baseUrl: String
        get() = BuildConfig.API_BASE_URL
}