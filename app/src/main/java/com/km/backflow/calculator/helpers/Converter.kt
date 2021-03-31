package com.km.backflow.calculator.helpers

import androidx.databinding.InverseMethod
import java.lang.NumberFormatException

object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(
        value: Int?
    ): String {
        // Converts Int to String.
        return value?.toString() ?: ""
    }

    @JvmStatic fun stringToInt(
        value: String
    ): Int? {
        // Converts String to Int.
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
}