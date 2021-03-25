package com.example.n_one.Activites

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import java.util.*

object TypefaceUtil {
    @JvmStatic
    fun overrideFont(
        context: Context,
        defaultFontNameToOverride: String,
        customFontFileNameInAssets: String
    ) {
        val customFontTypeface =
            Typeface.createFromAsset(context.assets, customFontFileNameInAssets)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val newMap: MutableMap<String, Typeface> = HashMap()
            newMap["serif"] = customFontTypeface
            try {
                val staticField = Typeface::class.java
                    .getDeclaredField("sSystemFontMap")
                staticField.isAccessible = true
                staticField[null] = newMap
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        } else {
            try {
                val defaultFontTypefaceField =
                    Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
                defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField[null] = customFontTypeface
            } catch (e: Exception) {
                Log.e(
                    TypefaceUtil::class.java.simpleName,
                    "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride"
                )
            }
        }
    }
}