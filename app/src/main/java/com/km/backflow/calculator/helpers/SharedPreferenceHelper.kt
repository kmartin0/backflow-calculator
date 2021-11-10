package com.km.backflow.calculator.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {
    companion object {
        private const val SHARED_PREFERENCE_FILE_KEY = "com.km.backflow.calculator.preferences"

        const val LAST_CHECKED_FOR_UPDATE_TIMESTAMP_KEY = "LAST_CHECKED_FOR_UPDATE_TIMESTAMP_KEY"

        fun getSharedPreferences(context: Context): SharedPreferences? {
            return context.getSharedPreferences(SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        }
    }
}