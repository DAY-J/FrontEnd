package com.example.dayj.util

import java.lang.reflect.Field


object BuildConfigFieldUtils {
    private const val GOOGLE_CLIENT_FIELD_NAME = "GOOGLE_CLIENT_ID"
    /**
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors
     * are used at the project level to set custom fields.
     * @param context       Used to find the correct file
     * @param fieldName     The name of the field-to-access
     * @return              The value of the field, or `null` if the field is not found.
     */
    private fun getBuildConfigValue(fieldName: String?): Any? {
        try {
            val clazz = Class.forName("com.example.dayj" + ".BuildConfig")
            val field: Field = clazz.getField(fieldName)
            return field.get(null)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    fun getGoogleClientFieldString() : String{
        return getBuildConfigString(GOOGLE_CLIENT_FIELD_NAME)
    }


    private fun getBuildConfigString(configField: String): String {
        val string = getBuildConfigValue(configField)
        return if(string is String) {
            string
        } else {
            ""
        }
    }
}