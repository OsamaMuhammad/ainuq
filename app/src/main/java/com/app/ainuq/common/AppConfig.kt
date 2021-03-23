package com.app.ainuq.common

import com.app.ainuq.BuildConfig
import javax.inject.Inject

interface AppConfig {
    val  baseUrl: String
    val gitRawUrl: String
}

class AppConfigImplementation @Inject constructor() : AppConfig {
    override val baseUrl: String
        get() = BuildConfig.API_BASE_URL

    override val gitRawUrl: String
        get() = "https://raw.githubusercontent.com/OsamaMuhammad/checking/master/frames/"
}