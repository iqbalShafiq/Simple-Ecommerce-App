package space.iqbalsyafiq.ecommerceapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        private var prefs: SharedPreferences? = null
        const val TOKEN = "TOKEN"

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveToken(token: String) {
        prefs?.edit(commit = true) { putString(TOKEN, token) }
    }

    fun getToken() = prefs?.getString(TOKEN, "Guest")

    fun clearToken() = prefs?.edit(commit = true) { clear() }
}