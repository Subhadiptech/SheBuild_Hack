package com.ersubhadip.helpers

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

class Storage {
    private var prefs: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null
    private var context: Context? = null

    fun Storage(context: Context) {
        this.context = context
        prefs = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        edit = prefs?.edit()
    }

    fun isNewUser(): Boolean {
        return prefs!!.getBoolean("isNewUser", true)
    }

    fun setIsNewUser(b: Boolean) {
        edit!!.putBoolean("isNewUser", b)
        edit!!.apply()
    }

    fun getEmergencyNumber(): Boolean {
        return prefs!!.getBoolean("number", false)
    }

    fun setEmergencyNumber(b: Boolean) {
        edit!!.putBoolean("number", b)
        edit!!.apply()
    }

}