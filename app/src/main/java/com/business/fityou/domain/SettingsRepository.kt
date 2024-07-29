package com.business.fitrack.domain

interface SettingsRepository {
    fun getString(name: String): String
    fun setString(name: String, value: String)
}